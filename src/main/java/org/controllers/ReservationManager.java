package org.controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.controllers.exceptions.ConnectionUnavailableException;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Person;
import org.models.Reservation;
import org.models.Room;

import oracle.jdbc.OracleTypes;

public class ReservationManager extends Manager<Reservation> {

	public ReservationManager() {
		super();
	}

	@Override
	public boolean filter(Reservation reservation, String criterea, String searchText) {
		if (searchText.isEmpty()) {
			return true;
		}

		// La chaîne n'est pas vide, donc vérifier le type de filtre
		switch (criterea) {
			case "Etat":
				Boolean paid = null;

				if ("payé".contains(searchText.toLowerCase()))
					paid = true;
				else if ("paye".contains(searchText.toLowerCase()))
					paid = true;
				else if ("impayé".contains(searchText.toLowerCase()))
					paid = false;
				else if ("impaye".contains(searchText.toLowerCase()))
					paid = false;

				if (paid == null)
					return true;
				return reservation.isPaid() == paid;
			case "Client":
				// verifier si le reservation est de maintenance
				if (reservation.getHotelClient() == null) {
					return true;
				}

				// verifier si le CIN est commencé par des 0 donc on doit le completer
				String clientHotel = "";
				int numberOfZeros = 8 - String.valueOf(reservation.getHotelClient()).length();
				for (int i = 0; i < numberOfZeros; i++) {
					clientHotel += "0";
				}
				clientHotel = clientHotel + String.valueOf(reservation.getHotelClient());

				return clientHotel.contains(StringNumberExtract.extract(searchText));
			case "Chambre":
				String room = String.valueOf(reservation.getRoom());
				return room.contains(StringNumberExtract.extract(searchText));
			default:
				return true;
		}
	}

	@Override
	protected Reservation dataFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_reservation");
		Date startDate = resultSet.getDate("date_debut");
		Date endDate = resultSet.getDate("date_fin");
		boolean isPaid = resultSet.getInt("paid") == 1 ? true : false;
		int employee = resultSet.getInt("employe");
		Integer hotelClient = resultSet.getInt("client_hotel");
		if (hotelClient == 0) {
			hotelClient = null; // Si le client est null, on le met à null (reservation de maintenance)
		}
		int room = resultSet.getInt("chambre");
		return new Reservation(id, startDate, endDate, isPaid, employee, hotelClient, room);
	}

	@Override
	protected String getSelectRequest() {
		return "{ call get_all_reservation(?) }";
	}

	@Override
	protected CallableStatement getInsertStatement(Reservation data) throws SQLException {
		CallableStatement stmt;
		String sql = "{ call add_reservation(?, ?, ?, ?, ?, ?) }";
		stmt = getConnection().prepareCall(sql);
		stmt.setDate(1, new Date(data.getStartDate().getTime())); // convertir Date en java.sql.Date
		stmt.setDate(2, new Date(data.getEndDate().getTime()));
		stmt.setInt(3, data.isPaid() ? 1 : 0);
		stmt.setInt(4, Controller.getInstance().getCurrentUser());
		if (data.getHotelClient() == null) {
			stmt.setNull(5, java.sql.Types.INTEGER); // Si le client est null, on le met à null (reservation de maintenance)
		} else {
			stmt.setInt(5, data.getHotelClient());
		}
		stmt.setInt(6, data.getRoom());
		return stmt;
	}

	@Override
	protected void insertInputValidation(Reservation data) throws ControllerException {
		ArrayList<Room> rooms = Controller.getInstance().getRoomManager().getData();
		ArrayList<Person> clients = Controller.getInstance().getClientManager().getData();

		// Sélectionner les chambres et les clients si elles sont vides
		if (rooms.isEmpty()) {
			Controller.getInstance().getRoomManager().select();
			rooms = Controller.getInstance().getRoomManager().getData();
		}

		if (clients.isEmpty()) {
			Controller.getInstance().getClientManager().select();
			clients = Controller.getInstance().getClientManager().getData();
		}

		// Verification des champs vides
		if (data.getStartDate() == null) {
			throw new ControllerException("Veuillez saisir une date de debut.");
		}
		if (data.getEndDate() == null) {
			throw new ControllerException("Veuillez saisir une date de fin.");
		}
		if (data.getHotelClient() != null && (data.getHotelClient() < 1 || data.getHotelClient() > 99999999)) {
			throw new ControllerException("Veuillez saisir un CIN valide.");
		}
		if (data.getRoom() <= 0) {
			throw new ControllerException("Veuillez saisir un numero de chambre valide.");
		}

		// Vérifier l'existence de la chambre
		boolean roomExists = false;
		for (Room room : rooms) {
			if (room.getId() == data.getRoom()) {
				roomExists = true;
				break;
			}
		}
		if (!roomExists) {
			throw new ControllerException("La chambre n'existe pas.");
		}

		// Vérifier l'existence du client
		if (data.getHotelClient() != null) {
			boolean clientExists = false;
			for (Person client : clients) {
				if (client.getId() == data.getHotelClient()) {
					clientExists = true;
					break;
				}
			}
			if (!clientExists) {
				throw new ControllerException("Le client n'existe pas.");
			}
		}

		// Vérifier la validité des dates
		if (data.getStartDate().after(data.getEndDate())) {
			throw new ControllerException("La date de début est après la date de fin.");
		}
		java.util.Date today = new java.util.Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DAY_OF_MONTH, -1); // soustraire un jour pour la comparaison
		today = calendar.getTime();
		if (data.getStartDate().before(calendar.getTime())) {
			throw new ControllerException("La date de début est dans le passé.");
		}

		// Vérifier la disponibilité de la chambre
		if (!reservationCheck(data.getRoom(), 0, data.getStartDate(), data.getEndDate())) {
			throw new ControllerException("La chambre est déjà réservée pour cette période.");
		}

	}

	// verifier si la chambre est disponible entre deux dates
	// reservationToModify est l'id de la reservation a modifier
	/**
	 * Checks if the time between {@code startDate} and {@code endDate} for the room
	 * with {@code roomId} is available and returns true if it is.
	 * {@code reservationToModify} will be ignored during the search.
	 */
	private boolean reservationCheck(int roomId, int reservationToModify, Date startDate, Date endDate)
			throws DBException {
		Connection connection = getConnection();

		if (connection == null) {
			throw new ConnectionUnavailableException();
		}

		CallableStatement stmt = null;
		boolean isValid = true;
		try {
			String sql = "{ call check_reservation(?, ?, ?) }";
			stmt = connection.prepareCall(sql);
			stmt.setInt(1, roomId);
			stmt.setInt(2, reservationToModify);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);

			stmt.execute();
			ResultSet result = null;
			result = (ResultSet) stmt.getObject(3);
			while (result.next()) {
				Date roomStartDate = result.getDate(1); // colonne 1 = date_debut
				Date roomEndDate = result.getDate(2); // colonne 2 = date_fin
				if (!endDate.before(roomStartDate) && !startDate.after(roomEndDate)) {
					isValid = false; // Indique que la réservation échoue
					break;
				}
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DBException();
		} finally {
			// Fermeture des ressources JDBC
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException exception) {
				exception.printStackTrace();
				throw new DBException();
			}
		}

		return isValid;
	}

	@Override
	protected String getDeleteRequest() {
		return "{ call delete_reservation(?) }";
	}

	@Override
	protected CallableStatement getUpdateStatement(Reservation data) throws SQLException {
		CallableStatement statement;
		String sql = "{ call modify_reservation(?, ?, ?, ?) }";
		statement = getConnection().prepareCall(sql);
		statement.setInt(1, data.getId());
		statement.setDate(2, new Date(data.getStartDate().getTime())); // convertir Date en java.sql.Date
		statement.setDate(3, new Date(data.getEndDate().getTime()));
		statement.setInt(4, data.isPaid() ? 1 : 0);
		return statement;
	}

	@Override
	protected void updateInputValidation(Reservation data) throws ControllerException {

		// Verification des champs vides
		if (data.getStartDate() == null) {
			throw new ControllerException("Veuillez saisir une date de debut.");
		}
		if (data.getEndDate() == null) {
			throw new ControllerException("Veuillez saisir une date de fin.");
		}
		if (data.getHotelClient() != null && (data.getHotelClient() <= 9999999 || data.getHotelClient() >= 100000000)) {
			throw new ControllerException("Veuillez saisir un CIN valide.");
		}
		if (data.getRoom() <= 0) {
			throw new ControllerException("Veuillez saisir un numero de chambre valide.");
		}

		// Vérifier la validité des dates
		if (data.getStartDate().after(data.getEndDate())) {
			System.err.println("La date de début est après la date de fin.");
			throw new ControllerException("La date de début est après la date de fin.");
		}

		// Vérifier la disponibilité de la chambre
		if (!reservationCheck(data.getRoom(), data.getId(), data.getStartDate(),
				data.getEndDate())) {
			System.err.println("La chambre est déjà réservée pour cette période.");
			throw new ControllerException("La chambre est réservée pour cette période.");
		}
	}
}
