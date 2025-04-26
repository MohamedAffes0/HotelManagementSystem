package org.models;

import java.sql.Date;
import java.util.ArrayList;

import org.app.StringNumberExtract;
import org.app.reservation.ReservationFilter;

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

	public void setStartDate(Date startDate) {
		if (startDate == null) {
			throw new IllegalArgumentException("Start date cannot be null.");
		}
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		if (endDate == null) {
			throw new IllegalArgumentException("End date cannot be null.");
		}
		if (startDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException("Start date cannot be after end date.");
		}
		this.endDate = endDate;
	}

	@Override
	public boolean filter(TextField search, String filterType) {
		String searchText = search.getText();

		if (searchText.isEmpty()) {
			return true;
		}

		// String is not empty so check filter type
		switch (filterType) {
			case "Est Payé":
				return ReservationFilter.isPaid(this, paidFromSearch(search.getText()));
			case "Client":
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return ReservationFilter.filterByClient(this, search.getText());
			case "Chambre":
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return ReservationFilter.filterByRoom(this, Integer.parseInt(search.getText()));
			default:
				return true;
		}
	}

	private boolean paidFromSearch(String searchText){

		if (searchText.isEmpty()) {
			return true;
		}

		if ("payé".contains(searchText.toLowerCase())) {
			return true;
		}

		if ("paye".contains(searchText.toLowerCase())) {
			return true;
		}

		if ("impayé".contains(searchText.toLowerCase())) {
			return false;
		}

		if ("impaye".contains(searchText.toLowerCase())) {
			return false;
		}

		return true;
	}

	@Override
	public ArrayList<ModelField> getFields() {
		ArrayList<ModelField> data = new ArrayList<>();

		//data.add(String.valueOf(getId()));
		data.add(new ModelField("Chambre " + getRoom() + " Du " + getStartDate().toString() + "Au" + getEndDate().toString(), null));
		String styleClass = "";
		if (isPaid()) {
			styleClass = "payed-badge";
		} else {
			styleClass = "unpaid-badge";
		}
		data.add(new ModelField(isPaid() ? "Payé" : "Impayé", styleClass));
		data.add(new ModelField("Client: " + getHotelClient(), null));
		data.add(new ModelField("Employée: " + getEmployee(), "employee-badge"));

		return data;
	}

}
