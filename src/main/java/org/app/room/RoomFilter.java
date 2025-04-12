package org.app.room;

import org.models.RoomModel;
import org.models.RoomModel.RoomState;

public class RoomFilter {

    public static boolean filterByPrice(RoomModel room, float price) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getPrice() <= price;
    }

    public static boolean filterByNumberOfPeople(RoomModel room, int numberOfPeople) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getNumberOfPeople() >= numberOfPeople;
    }

    public static boolean filterById(RoomModel room, int id) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getId() == id;
    }

    public static boolean filterByFloor(RoomModel room, int floor) {

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getFloor() == floor;
    }

    public static boolean filterByType(RoomModel room, String roomType) {
        //--simple, double, suite--

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getRoomType().equals(roomType);
    }

    public static boolean filterByState(RoomModel room, RoomState state) {
        //--LIBRE, OCCUPEE, MAINTENANCE--

        if (room == null) {
            System.err.println("Erreur lors de la récupération de la chambre.");
            return false;
        }

        return room.getState() == state;
    }
}
