package org.models;

import java.sql.Date;
import java.util.ArrayList;

import org.controllers.StringNumberExtract;
import org.controllers.reservation.ReservationFilter;

import javafx.scene.control.TextField;

public class Reservation extends Model {
	private int id;
	private Date startDate;
	private Date endDate;
	private boolean paid;
	private int employee;
	private int hotelClient;
	private int room;

	public Reservation(int id, Date startDate, Date endDate, boolean paid, int employee, int hotelClient,
			int room) {
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

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public ArrayList<ModelField> getFields() {
		ArrayList<ModelField> data = new ArrayList<>();

		// data.add(String.valueOf(getId()));
		data.add(new ModelField("Chambre " + getRoom() + " Du  " + getStartDate().toString() + "  Au  "
				+ getEndDate().toString(), null));
		String styleClass = "";
		if (isPaid()) {
			styleClass = "payed-badge";
		} else {
			styleClass = "unpaid-badge";
		}
		data.add(new ModelField(isPaid() ? "Payé" : "Impayé", styleClass));
		data.add(new ModelField("Employée: " + getEmployee(), "employee-badge"));

		String clientHotel = "";
		int numberOfZeros = 8 - String.valueOf(getHotelClient()).length();
		for (int i = 0; i < numberOfZeros; i++) {
			clientHotel += "0";
		}
		clientHotel = clientHotel + String.valueOf(getHotelClient());
		data.add(new ModelField("Client: " + clientHotel, "client-badge"));

		return data;
	}

}
