package org.app.room;

import org.models.Room;
import org.models.Room.RoomState;
import org.models.Room.RoomType;

public class RoomFilter {

    public static boolean filterByPrice(Room room, float price) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getPrice() <= price;
    }

    public static boolean filterByNumberOfPeople(Room room, int numberOfPeople) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getCapacity() >= numberOfPeople;
    }

    public static boolean filterById(Room room, int id) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getId() == id;
    }

    public static boolean filterByFloor(Room room, int floor) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getFloor() == floor;
    }

    public static boolean filterByType(Room room, RoomType roomType) {
        //--simple, double, suite--

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getRoomType().equals(roomType);
    }

    public static boolean filterByState(Room room, RoomState state) {
        //--LIBRE, OCCUPEE, MAINTENANCE--

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getState() == state;
    }
}
