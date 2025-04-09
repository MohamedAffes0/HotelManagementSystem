package org.app;

import java.util.ArrayList;

import org.models.RoomModel;

public class RoomFilter {
    public static ArrayList<RoomModel> FilterByPrice(float price) {
        ArrayList<RoomModel> rooms = RoomSelect.roomSelect();
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getPrix() <= price) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterByNumberOfPeople(int nb_people) {
        ArrayList<RoomModel> rooms = RoomSelect.roomSelect();
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getNbPersonnes() <= nb_people) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterById(int id) {
        ArrayList<RoomModel> rooms = RoomSelect.roomSelect();
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getIdChambre() == id) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterByFloor(int etage) {
        ArrayList<RoomModel> rooms = RoomSelect.roomSelect();
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getEtage() == etage) {
                    filteredRooms.add(rooms.get(i));
                }
            }
        } else {
            System.err.println("Erreur lors de la récupération des chambres.");
            return null; // Indique que la récupération des chambres a échoué
        }

        return filteredRooms;
    }

    public static ArrayList<RoomModel> FilterByType(String type) {
        //--simple, double, suite--
        ArrayList<RoomModel> rooms = RoomSelect.roomSelect();
        ArrayList<RoomModel> filteredRooms = new ArrayList<>();

        if (rooms != null) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getTypeChambre().equals(type)) {
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
    //     // ArrayList<RoomModel> filteredRooms = FilterByPrice(901);
    //     // ArrayList<RoomModel> filteredRooms = FilterByNumberOfPeople(10);
    //     // ArrayList<RoomModel> filteredRooms = FilterById(10);
    //     // ArrayList<RoomModel> filteredRooms = FilterByFloor(1);
    //     ArrayList<RoomModel> filteredRooms = FilterByType("simple");
    //     if (filteredRooms != null && filteredRooms.size() > 0) {
    //         for (RoomModel room : filteredRooms) {
    //             System.out.println("Chambre ID: " + room.getIdChambre() + ", Prix: " + room.getPrix());
    //         }
    //     } else {
    //         System.out.println("Aucune chambre trouvée dans la plage de prix spécifiée.");
    //     }
    // }
}
