package org.controllers.reservation;

import org.models.Reservation;

public class ReservationFilter {

    public static boolean isPaid(Reservation reservation, boolean isPaid) {

        if (reservation == null) {
            System.err.println("Erreur lors de la récupération de la reservation.");
            return false;
        }

        return reservation.isPaid() == isPaid;
    }

    public static boolean filterByClient(Reservation reservation, String clientId) {

        if (reservation == null) {
            System.err.println("Erreur lors de la récupération de la reservation.");
            return false;
        }

        if (clientId.isEmpty()) {
            return true;
        }

        String reservationClientId = String.valueOf(reservation.getHotelClient());
        return reservationClientId.contains(clientId);
    }

    public static boolean filterByRoom(Reservation reservation, int roomId) {

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
