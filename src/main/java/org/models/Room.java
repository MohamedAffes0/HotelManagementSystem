package org.models;

public class Room {
    public static enum RoomState{
        LIBRE,
        OCCUPEE,
        MAINTENANCE
    }

    public static enum RoomType{
        SIMPLE,
        DOUBLE,
        SUITE
    }

    private int id;
    private RoomType roomType;
    private int floor;
    private int capacity;
    private float price;
    private RoomState state; //-- 0 for libre, 1 for occupée --, 2 for maintenance --

    public Room(int id, RoomType roomType, int floor, int capacity, float price, RoomState state){
        
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID ne doit pas être vide.");
        }

        this.id = id;
        this.roomType = roomType;
        this.floor = floor;
        this.capacity = capacity;
        this.price = price;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getFloor() {
        return floor;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getPrice() {
        return price;
    }

    public RoomState getState() {
        return state;
    }
    
    public void setState(RoomState state) {
        this.state = state;
    }
}
