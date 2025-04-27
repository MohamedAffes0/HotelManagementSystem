package org.app.room;

import java.util.ArrayList;
import java.util.HashMap;

import org.app.reservation.ReservationSelect;
import org.models.Reservation;
import org.models.Room;

public class MostCovetedRooms {

    public static HashMap<Integer, Integer> roomReservationCount(ArrayList<Reservation> reservations) {
        HashMap<Integer, Integer> reservationCount = new HashMap<>();
        for (int i = 0; i < reservations.size(); i++) {
            int currentReservation = reservations.get(i).getRoom();
            if (reservationCount.containsKey(currentReservation)) {
                reservationCount.replace(currentReservation, reservationCount.get(currentReservation) + 1);
            }
            else {
                reservationCount.put(currentReservation, 1);
            }
        }
        return reservationCount;
    }

    public static ArrayList<Room> getMostCovetedRooms(ArrayList<Room> rooms, 
            HashMap<Integer, Integer> reservationCount) {
        ArrayList<Room> mostCovetedRooms = new ArrayList<>();
        int totalReservations = 0;
        for (int i = 0; i < rooms.size(); i++) {
            Integer count = reservationCount.get(rooms.get(i).getId());
            if (count != null) {
                totalReservations += count;
            }
        }

        double threshold = totalReservations * 0.1; // 10% of total reservations 

        for (int i = 0; i < rooms.size(); i++) {
            int roomId = rooms.get(i).getId();
            if (reservationCount.containsKey(roomId) && reservationCount.get(roomId) >= threshold) {
                mostCovetedRooms.add(rooms.get(i));
            }
        }

        return mostCovetedRooms;
    }

    // public static void main(String[] args) {
    //     // Test the roomReservationCount method
    //     ArrayList<Reservation> reservations = ReservationSelect.dataFromDB(); // Replace with actual reservation data

    //     HashMap<Integer, Integer> reservationCount = roomReservationCount(reservations);
    //     System.out.println(reservationCount);

    //     // Test the getMostCovetedRooms method
    //     ArrayList<Room> rooms = RoomSelect.dataFromDB(); // Replace with actual room data
    //     ArrayList<Room> mostCovetedRooms = getMostCovetedRooms(rooms, reservationCount);
    //     System.out.println("Most coveted rooms: ");
    //     for (Room room : mostCovetedRooms) {
    //         System.out.println(room.getId());
    //     }
    // }
}
