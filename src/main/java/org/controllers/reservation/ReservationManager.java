package org.controllers.reservation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.controllers.Controller;
import org.controllers.Manager;
import org.controllers.exceptions.ConnectionUnavailableException;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.controllers.reservation.ReservationChecker.ReservationDate;
import org.models.Employee;
import org.models.Person;
import org.models.Reservation;
import org.models.Room;

import oracle.jdbc.OracleTypes;

public class ReservationManager extends Manager<Reservation> {

	public ReservationManager() {
		super();
	}

	@Override
	protected Reservation dataFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_reservation");
		Date startDate = resultSet.getDate("date_debut");
		Date endDate = resultSet.getDate("date_fin");
		boolean isPaid = resultSet.getInt("paid") == 1 ? true : false;
		int employee = resultSet.getInt("employe");
		int hotelClient = resultSet.getInt("client_hotel");
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
		stmt.setInt(4, data.getEmployee());
		stmt.setInt(5, data.getHotelClient());
		stmt.setInt(6, data.getRoom());
		return stmt;
	}

	@Override
	protected void insertInputValidation(Reservation data) throws ControllerException {

		ArrayList<Room> rooms = Controller.getInstance().getRoomManager().getData();
		ArrayList<Person> clients = Controller.getInstance().getClientManager().getData();

		// Verification des champs vides
		if (data.getStartDate() == null) {
			throw new ControllerException("Veuillez saisir une date de debut.");
		}
		if (data.getEndDate() == null) {
			throw new ControllerException("Veuillez saisir une date de fin.");
		}
		if (data.getHotelClient() <= 9999999 || data.getHotelClient() >= 100000000) {
			throw new ControllerException("Veuillez saisir un CIN valide.");
		}
		if (data.getRoom() <= 0) {
			throw new ControllerException("Veuillez saisir un numero de chambre valide.");
		}

		// Check room existence
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

		// Check client existence
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

		// Check date validity
		if (data.getStartDate().after(data.getEndDate())) {
			System.err.println("La date de début est après la date de fin.");
			throw new ControllerException("La date de début est après la date de fin.");
		}
		java.util.Date today = new java.util.Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance(); calendar.setTime(today);
		calendar.add(Calendar.DAY_OF_MONTH, -1); // soustraire un jour pour la comparaison
		today = calendar.getTime();
		if (data.getStartDate().before(calendar.getTime())) {
			System.err.println("La date de début est dans le passé.");
			throw new ControllerException("La date de début est dans le passé.");
		}

		// Check room availability
		if (reservationCheck(data.getRoom(), 0, data.getStartDate(), data.getEndDate())) {
			System.err.println("La chambre est déjà réservée pour cette période.");
			throw new ControllerException("La chambre est déjà réservée pour cette période.");
		}

	}

	/**
	 * Checks if the time between {@code startDate} and {@code endDate} for the room
	 * with {@code roomId} is available and returns true if it is.
	 * {@code reservationToAdd} will be ignored during the search.
	 */
	private boolean reservationCheck(int roomId, int reservationToAdd, Date startDate, Date endDate)
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
			stmt.setInt(2, reservationToAdd);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);

			stmt.execute();
			ResultSet result = null;
			result = (ResultSet) stmt.getObject(3);
			while (result.next()) {
				Date roomStartDate = result.getDate(1); // colonne 1 = date_debut
				Date roomEndDate = result.getDate(2); // colonne 2 = date_fin
				// TODO fix this condition
				if (endDate.before(roomStartDate) || startDate.after(roomEndDate)) {
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
		if (data.getHotelClient() <= 9999999 || data.getHotelClient() >= 100000000) {
			throw new ControllerException("Veuillez saisir un CIN valide.");
		}
		if (data.getRoom() <= 0) {
			throw new ControllerException("Veuillez saisir un numero de chambre valide.");
		}

		// Check date validity
		if (data.getStartDate().after(data.getEndDate())) {
			System.err.println("La date de début est après la date de fin.");
			throw new ControllerException("La date de début est après la date de fin.");
		}
		java.util.Date today = new java.util.Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DAY_OF_MONTH, -1); // soustraire un jour pour la comparaison
		today = calendar.getTime();
		if (data.getStartDate().before(calendar.getTime())) {
			System.err.println("La date de début est dans le passé.");
			throw new ControllerException("La date de début est dans le passé.");
		}

		// Check room availability
		if (!reservationCheck(data.getRoom(), data.getRoom(), data.getStartDate(),
				data.getEndDate())) {
			System.err.println("La chambre est déjà réservée pour cette période.");
			throw new ControllerException("La chambre est déjà réservée pour cette période.");
		}
	}
}
