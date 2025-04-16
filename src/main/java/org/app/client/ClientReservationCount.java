package org.app.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.models.Person;
import org.models.Reservation;

public class ClientReservationCount {

    public static HashMap<Integer, Integer> clientReservationCount(ArrayList<Reservation> reservations) {
        HashMap<Integer, Integer> reservationCount = new HashMap<>();
        for (int i = 0; i < reservations.size(); i++) {
            int currentClient = reservations.get(i).getHotelClient();
            if (reservationCount.containsKey(currentClient)) {
                reservationCount.replace(currentClient, reservationCount.get(currentClient) + 1);
            }
            else {
                reservationCount.put(currentClient, 1);
            }
        }
        return reservationCount;
    }

    public static ArrayList<Person> getFaithfulClients(ArrayList<Person> clients, 
            HashMap<Integer, Integer> reservationCount) {
        ArrayList<Person> faithfulClients = new ArrayList<>();
        int totalReservations = 0;
        for (int i = 0; i < clients.size(); i++) {
            totalReservations += reservationCount.get(clients.get(i).getCin());
        }

        double threshold = totalReservations * 0.1; // 10% of total reservations 

        for (int i = 0; i < clients.size(); i++) {
            int clientId = clients.get(i).getCin();
            if (reservationCount.containsKey(clientId) && reservationCount.get(clientId) >= threshold) {
                faithfulClients.add(clients.get(i));
            }
        }

        return faithfulClients;
    }

    // public static void main(String[] args) {
    //     // Test the clientReservationCount method
    //     ArrayList<ReservationModel> reservations = ReservationSelect.reservationSelect();

    //     HashMap<Integer, Integer> reservationCount = clientReservationCount(reservations);
    //     System.out.println(reservationCount);

    //     // Test the getFaithfulClients method
    //     ArrayList<PersonModel> clients = ClientSelect.clientSelect();
    //     ArrayList<PersonModel> faithfulClients = getFaithfulClients(clients, reservationCount);
    //     System.out.println("Faithful clients: ");
    //     for (PersonModel client : faithfulClients) {
    //         System.out.println(client.getId() + " " + client.getName() + " " + client.getLastName() + " " + client.getMail());
    //     }
    // }
}
