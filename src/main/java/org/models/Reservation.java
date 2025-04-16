package org.models;

import java.sql.Date;

public class Reservation {
    private int id;
    private Date startDate;
    private Date endDate;
    private boolean paid;
    private int employee;
    private int hotelClient;
    private int room;

    public Reservation(int id, Date startDate, Date endDate, boolean paid, int employee, int hotelClient, int room) {
        
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null.");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paid = paid;
        this.employee = employee;
        this.hotelClient = hotelClient;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getEmployee() {
        return employee;
    }

    public int getHotelClient() {
        return hotelClient;
    }

    public int getRoom() {
        return room;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
