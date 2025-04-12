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

}
