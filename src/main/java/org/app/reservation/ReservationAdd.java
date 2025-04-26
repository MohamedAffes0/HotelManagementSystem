package org.app.reservation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import org.app.client.ClientChecker;
import org.app.client.ClientChecker.ClientStatus;
import org.app.reservation.ReservationChecker.ReservationDate;
import org.database.DBConnect;
import org.models.Reservation;
import org.models.Room;

public class ReservationAdd {

    public static enum CreationStatus {
        SUCCESS,
        DB_PROBLEM,
        ROOM_NON_EXISTENT,
        ROOM_NOT_AVAILABLE,
        DATE_NOT_AVAILABLE,
        CLIENT_NON_EXISTENT,
    }

    // id a ignorer
    public static CreationStatus reservationAdd(Reservation reservation, ArrayList<Room> rooms) {

        if (ClientChecker.clientCheck(reservation.getHotelClient()) == ClientStatus.CLIENT_NOT_FOUND) {
            System.err.println("Le client n'existe pas.");
            return CreationStatus.CLIENT_NON_EXISTENT; // Indique que le client n'existe pas
        }

        // verification de la date de debut et de fin
        if (reservation.getStartDate().after(reservation.getEndDate())) {
            System.err.println("La date de début est après la date de fin.");
            return CreationStatus.DATE_NOT_AVAILABLE; // Indique que la date n'est pas disponible
        }
        java.util.Date today = new java.util.Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // soustraire un jour pour la comparaison
        today = calendar.getTime();
        if (reservation.getStartDate().before(calendar.getTime())) {
            System.err.println("La date de début est dans le passé.");
            return CreationStatus.DATE_NOT_AVAILABLE; // Indique que la date n'est pas disponible
        }

        // verification de l'existence de la chambre
        boolean roomExists = false;
        for (Room room : rooms) {
            if (room.getId() == reservation.getRoom()) {
                roomExists = true;
                break;
            }
        }
        if (!roomExists) {
            System.err.println("La chambre n'existe pas.");
            return CreationStatus.ROOM_NON_EXISTENT; // Indique que la chambre n'existe pas
        }

        // verification de la disponibilité de la chambre
        ReservationDate reservationDate = new ReservationDate(reservation.getStartDate(), reservation.getEndDate());
        if (ReservationChecker.reservationCheck(reservation.getRoom(), 0, reservationDate) == false) {
            System.err.println("La chambre est déjà réservée pour cette période.");
            return CreationStatus.ROOM_NOT_AVAILABLE; // Indique que la réservation échoue
        }

        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return CreationStatus.DB_PROBLEM; // Indique que la connexion a échoué
            }

            String sql = "{ call add_reservation(?, ?, ?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);

            stmt.setDate(1, new Date(reservation.getStartDate().getTime())); // convertir Date en java.sql.Date
            stmt.setDate(2, new Date(reservation.getEndDate().getTime()));
            stmt.setInt(3, reservation.isPaid() ? 1 : 0);
            stmt.setInt(4, reservation.getEmployee());
            stmt.setInt(5, reservation.getHotelClient());
            stmt.setInt(6, reservation.getRoom());

            stmt.execute();
            return CreationStatus.SUCCESS; // Indique que l'ajout a réussi

        } catch (SQLException exception) {
            exception.printStackTrace();
            return CreationStatus.DB_PROBLEM; // Indique que la connexion a échoué
        } finally {
            // toujour executer le bloc finally
            // Fermeture des ressources JDBC
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //     // Test de la méthode reservationAdd
    //     Reservation reservation = new Reservation(1, Date.valueOf("2026-12-23"), Date.valueOf("2026-12-24"),
    //             false, 3, 1234, 3);
    //     CreationStatus result = reservationAdd(reservation, RoomSelect.dataFromDB());
    //     System.out.println("Reservation status: " + result);
    // }
}
