package org.app.room;

import org.models.RoomModel;
import org.models.RoomModel.RoomState;

public class RoomFilter {
    
    public static boolean FilterByPrice(RoomModel room, float price) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getPrice() <= price;
    }

    public static boolean FilterByNumberOfPeople(RoomModel room, int numberOfPeople) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getNumberOfPeople() >= numberOfPeople;
    }

    public static boolean FilterById(RoomModel room, int id) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getId() == id;
    }

    public static boolean FilterByFloor(RoomModel room, int floor) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getFloor() == floor;
    }

    public static boolean FilterByType(RoomModel room, String roomType) {
        //--simple, double, suite--

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getRoomType().equals(roomType);
    }

    public static boolean FilterByState(RoomModel room, RoomState state) {
        //--LIBRE, OCCUPEE, MAINTENANCE--

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getState() == state;
    }
}
