package org.app;

import java.util.ArrayList;
import java.util.HashMap;

import org.models.PersonModel;
import org.models.ReservationModel;

public class ClientReservationCount {

    public static HashMap<Integer, Integer> clientReservationCount(ArrayList<ReservationModel> reservations) {
        HashMap<Integer, Integer> reservationCount = new HashMap<>();
        for (int i = 0; i < reservations.size(); i++) {
            int currentClient = reservations.get(i).getHotelClient();
            if (reservationCount.get(currentClient) != null) {
                reservationCount.put(currentClient, 1);
            }
            else {
                reservationCount.replace(currentClient, reservationCount.get(currentClient) + 1);
            }
        }
        return reservationCount;
    }

    public static ArrayList<PersonModel> getFaithfulClients(ArrayList<PersonModel> clients, 
            HashMap<Integer, Integer> reservationCount) {
        ArrayList<PersonModel> faithfulClients = new ArrayList<>();
        int numberOfReservations = 0;
        for (int i = 0; i < clients.size(); i++) {
            numberOfReservations += reservationCount.get(clients.get(i).getId());
        }
        for (int i = 0; i < clients.size(); i++) {
            if (reservationCount.get(clients.get(i).getId()) >= numberOfReservations * 0.1) {
                faithfulClients.add(clients.get(i));
            }
        }
        return faithfulClients;
    }
}
