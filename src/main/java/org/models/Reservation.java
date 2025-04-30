package org.models;

import java.sql.Date;
import java.util.ArrayList;

public class Reservation extends Model {
	private int id;
	private Date startDate;
	private Date endDate;
	private boolean paid;
	private int employee;
	private Integer hotelClient;
	private int room;

	public Reservation(int id, Date startDate, Date endDate, boolean paid, int employee, Integer hotelClient,
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

	public Integer getHotelClient() {
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

	// retourner les champs de la reservation pour l'affichage dans la liste
	@Override
	public ArrayList<ModelField> getFields() {
		ArrayList<ModelField> data = new ArrayList<>();

		data.add(new ModelField("Chambre " + getRoom() + " Du  " + getStartDate().toString() + "  Au  "
				+ getEndDate().toString(), null));

		// Si la reservation est de client (non maintenance), on affiche le badge
		if (getHotelClient() != null) {
			String styleClass = "";
			if (isPaid()) {
				styleClass = "payed-badge";
			} else {
				styleClass = "unpaid-badge";
			}
			data.add(new ModelField(isPaid() ? "Payé" : "Impayé", styleClass));
		}
		data.add(new ModelField("Employée: " + getEmployee(), "employee-badge"));

		// si la reservation de maintenance, on affiche le badge de maintenance
		// sinon on affiche le badge du client
		if (getHotelClient() == null) {
			data.add(new ModelField("Maintenance", "label-maintenance-reservation"));
		} else {
			String clientHotel = "";
			int numberOfZeros = 8 - String.valueOf(getHotelClient()).length();
			for (int i = 0; i < numberOfZeros; i++) {
				clientHotel += "0";
			}
			clientHotel = clientHotel + String.valueOf(getHotelClient());
			data.add(new ModelField("Client: " + clientHotel, "client-badge"));
		}
		
		return data;
	}

}
