package org.models;

public class RoomModel {
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
    private int numberOfPeople;
    private float price;
    private RoomState state; //-- 0 for libre, 1 for occupée --, 2 for maintenance --

    public RoomModel(int id, RoomType roomType, int floor, int numberOfPeople, float price, RoomState state){
        
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID ne doit pas être vide.");
        }

        this.id = id;
        this.roomType = roomType;
        this.floor = floor;
        this.numberOfPeople = numberOfPeople;
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

    public int getNumberOfPeople() {
        return numberOfPeople;
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
