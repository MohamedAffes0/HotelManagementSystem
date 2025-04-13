package org.app.reservation;

import org.models.ReservationModel;

public class ReservationFilter {

    public static boolean isPaid(ReservationModel reservation, boolean isPaid) {

        if (reservation == null) {
            System.err.println("Erreur lors de la récupération de la reservation.");
            return false;
        }

        return reservation.isPaid() == isPaid;
    }

    public static boolean filterByClient(ReservationModel reservation, int clientId) {

        if (reservation == null) {
            System.err.println("Erreur lors de la récupération de la reservation.");
            return false;
        }

        return reservation.getHotelClient() == clientId;
    }

    public static boolean filterByRoom(ReservationModel reservation, int roomId) {

        if (reservation == null) {
            System.err.println("Erreur lors de la récupération de la reservation.");
            return false;
        }

        return reservation.getRoom() == roomId;
    }

    // public static void main(String[] args) {
    //     // Test de la classe ReservationFilter
    //     ReservationModel reservation = new ReservationModel(1, Date.valueOf("2023-10-01"), Date.valueOf("2023-10-05"), true, 1, 1, 500);
        
    //     System.out.println("Test de la méthode isPaid: " + isPaid(reservation, true));
    //     System.out.println("Test de la méthode filterByClient: " + filterByClient(reservation, 1));
    //     System.out.println("Test de la méthode filterByRoom: " + filterByRoom(reservation, 500));
    // }
}
