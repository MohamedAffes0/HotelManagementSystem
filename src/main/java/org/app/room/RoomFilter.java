package org.app.room;

import java.util.ArrayList;

import org.models.RoomModel;
import org.models.RoomModel.RoomState;

public class RoomFilter {
    public static ArrayList<RoomModel> FilterByPrice(ArrayList<RoomModel> rooms, float price) {
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getPrice() <= price) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterByNumberOfPeople(ArrayList<RoomModel> rooms, int numberOfPeople) {
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getNumberOfPeople() <= numberOfPeople) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterById(ArrayList<RoomModel> rooms, int id) {
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getId() == id) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterByFloor(ArrayList<RoomModel> rooms, int floor) {
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getFloor() == floor) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterByType(ArrayList<RoomModel> rooms, String roomType) {
        //--simple, double, suite--
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getRoomType().equals(roomType)) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterByState(ArrayList<RoomModel> rooms, RoomState state) {
        //--LIBRE, OCCUPEE, MAINTENANCE--
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getState() == state) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    // public static void main(String[] args) {
        // ArrayList<RoomModel> filteredRooms = FilterByPrice(901);
        // ArrayList<RoomModel> filteredRooms = FilterByNumberOfPeople(10);
        // ArrayList<RoomModel> filteredRooms = FilterById(10);
        // ArrayList<RoomModel> filteredRooms = FilterByFloor(1);
        // ArrayList<RoomModel> filteredRooms = FilterByType("simple");
        // ArrayList<RoomModel> filteredRooms = FilterByState(RoomState.OCCUPEE);
        // if (filteredRooms != null && filteredRooms.size() > 0) {
        //     for (RoomModel room : filteredRooms) {
        //         System.out.println("Chambre ID: " + room.getIdChambre() + ", Prix: " + room.getPrix());
        //     }
        // } else {
        //     System.out.println("Aucune chambre trouvée dans la plage de prix spécifiée.");
        // }
    // }
}
