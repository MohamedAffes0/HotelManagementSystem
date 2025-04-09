package org.models;

import java.sql.Date;

public class ReservationModel {
    private int id_reservation;
    private Date date_debut;
    private Date date_fin;
    private boolean paid;
    private int employe;
    private int client_hotel;
    private int chambre;

    public ReservationModel(int id, Date d_debut, Date d_fin, boolean paid, int employe, int client_hotel, int chambre) {
        this.id_reservation = id;
        this.date_debut = d_debut;
        this.date_fin = d_fin;
        this.paid = paid;
        this.employe = employe;
        this.client_hotel = client_hotel;
        this.chambre = chambre;
    }

    public int getIdReservation() {
        return id_reservation;
    }

    public Date getDateDebut() {
        return date_debut;
    }

    public Date getDateFin() {
        return date_fin;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getEmploye() {
        return employe;
    }

    public int getClientHotel() {
        return client_hotel;
    }

    public int getChambre() {
        return chambre;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
