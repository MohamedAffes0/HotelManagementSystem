package org.controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.controllers.exceptions.ConnectionUnavailableException;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Reservation;
import org.models.Room;
import org.models.Room.RoomState;
import org.models.Room.RoomType;

import oracle.jdbc.OracleTypes;

/**
 * UserManager
 */
public class RoomManager extends Manager<Room> {

	public RoomManager() {
		super();
	}

	/**
	 * Returns an Array list containing the top 10% most visited rooms.
	 * 
	 * @return
	 * @throws DBException
	 */
	public ArrayList<Room> getMostCovetedRooms() throws DBException {
		HashMap<Integer, Integer> reservationCount = reservationCount();
		ArrayList<Room> mostCovetedRooms = new ArrayList<>();
		int totalReservations = 0;

		if (getData().isEmpty()) {
			select();
		}

		for (Room room : getData()) {
			Integer count = reservationCount.get(room.getId());
			if (count != null) {
				totalReservations += count;
			}
		}

		double threshold = totalReservations * 0.1; // 10% of total reservations

		for (Room room : getData()) {
			int roomId = room.getId();
			if (reservationCount.containsKey(roomId) && reservationCount.get(roomId) >= threshold) {
				mostCovetedRooms.add(room);
			}
		}

		return mostCovetedRooms;
	}

	/**
	 * Returns an {@code ArrayList} of room ids that are available from
	 * {@code startDate} to
	 * {@code endDate} and that have {@code capacity}.
	 */
	public ArrayList<Integer> getAvaliableRooms(Date startDate, Date endDate, int capacity)
			throws ControllerException {
		Connection connection = getConnection();

		if (connection == null) {
			throw new ConnectionUnavailableException();
		}

		if (startDate == null || endDate == null) {
			new ControllerException("Les dates de début et de fin ne doivent pas être nulles.");
		}

		if (startDate.after(endDate)) {
			new ControllerException("La date de début doit préceder la date de fin.");
		}

		ArrayList<Integer> availableRooms = new ArrayList<>();
		CallableStatement stmt = null;
		ResultSet resultSet = null;

		try {
			String sql = "{ ? = call get_available_rooms(?, ?, ?) }";
			stmt = connection.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setDate(2, startDate);
			stmt.setDate(3, endDate);
			stmt.setInt(4, capacity);
			stmt.execute();

			resultSet = (ResultSet) stmt.getObject(1);
			while (resultSet.next()) {
				int roomId = resultSet.getInt("id_chambre");
				availableRooms.add(roomId);
			}

			return availableRooms;
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DBException();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException exception) {
				exception.printStackTrace();
				throw new DBException();
			}
		}
	}

	/**
	 * Calculates the number of times each room was reserved.
	 * 
	 * @return HashMap<roomId, number of reservations>
	 * @throws DBException
	 */
	private HashMap<Integer, Integer> reservationCount() throws DBException {
		ArrayList<Reservation> reservations = Controller.getInstance().getReservationManager().getData();

		if (reservations.isEmpty()) {
			Controller.getInstance().getReservationManager().select();
			reservations = Controller.getInstance().getReservationManager().getData();
		}

		HashMap<Integer, Integer> reservationCount = new HashMap<>();

		for (Reservation reservation : reservations) {
			int currentReservation = reservation.getRoom();
			if (reservationCount.containsKey(currentReservation)) {
				reservationCount.replace(currentReservation,
						reservationCount.get(currentReservation) + 1);
			} else {
				reservationCount.put(currentReservation, 1);
			}
		}
		return reservationCount;
	}

	@Override
	public boolean filter(Room room, String criterea, String searchText) {
		if (searchText.isEmpty()) {
			return true;
		}
		// String is not empty so check filter type
		switch (criterea) {
			case "Etage":
				return room.getFloor() == Integer.parseInt(StringNumberExtract.extract(searchText));
			case "Capacité":
				int numberOfPeople = Integer.parseInt(StringNumberExtract.extract(searchText));
				return room.getCapacity() >= numberOfPeople;
			case "Prix":
				float price = Float.parseFloat(StringFloatExtract.extract(searchText));
				return room.getPrice() <= price;
			case "Type":
				searchText = searchText.toLowerCase();

				RoomType roomType = null;
				// Return true if searchText = "s" as both "simple" and "suite" start with "s"
				if (searchText.equals("s"))
					return true;

				// Simple
				if ("simple".contains(searchText))
					roomType = RoomType.SIMPLE;
				if (searchText.contains("simple"))
					roomType = RoomType.SIMPLE;

				// Suite
				if (searchText.contains("suite"))
					roomType = RoomType.SUITE;
				if ("suite".contains(searchText))
					roomType = RoomType.SUITE;

				// Double
				if ("double".contains(searchText))
					roomType = RoomType.DOUBLE;
				if (searchText.contains("double"))
					roomType = RoomType.DOUBLE;

				// Result
				if (roomType == null)
					return false;
				else
					return room.getRoomType().equals(roomType);
			case "Etat":
				searchText = searchText.toLowerCase();
				RoomState state = null;

				// Occupée
				if ("occupée".contains(searchText))
					state = RoomState.OCCUPEE;
				if ("occupee".contains(searchText))
					state = RoomState.OCCUPEE;
				if (searchText.contains("occupée"))
					state = RoomState.OCCUPEE;
				if (searchText.contains("occupee"))
					state = RoomState.OCCUPEE;

				// Libre
				if ("libre".contains(searchText.toLowerCase()))
					state = RoomState.LIBRE;
				if (searchText.toLowerCase().contains("libre"))
					state = RoomState.LIBRE;

				// Maintenance
				if ("maintenance".contains(searchText.toLowerCase()))
					state = RoomState.MAINTENANCE;
				if (searchText.toLowerCase().contains("maintenance"))
					state = RoomState.MAINTENANCE;

				if (state == null) {
					return false;
				} else {
					return room.getState() == state;
				}
			default:
				return true;
		}
	}

	@Override
	protected Room dataFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_chambre");
		RoomType roomType;
		switch (resultSet.getString("type_chambre")) {
			case "simple":
				roomType = RoomType.SIMPLE;
				break;
			case "double":
				roomType = RoomType.DOUBLE;
				break;
			case "suite":
				roomType = RoomType.SUITE;
				break;
			default:
				// Retourner simple si le type n'est pas reconnu
				roomType = RoomType.SIMPLE;
				break;
		}
		int floor = resultSet.getInt("etage");
		int numberOfPeople = resultSet.getInt("nb_personnes");
		float price = resultSet.getFloat("prix");
		RoomState state;
		switch (resultSet.getInt("etat")) {
			case 0:
				state = RoomState.LIBRE;
				break;
			case 1:
				state = RoomState.OCCUPEE;
				break;
			case 2:
				state = RoomState.MAINTENANCE;
				break;
			default:
				// Retourner libre si l'état n'est pas reconnu
				state = RoomState.LIBRE;
				break;
		}
		return new Room(id, roomType, floor, numberOfPeople, price, state);
	}

	@Override
	protected String getSelectRequest() {
		return "{ call get_all_rooms(?) }";
	}

	@Override
	protected CallableStatement getInsertStatement(Room room) throws SQLException {
		CallableStatement stmt;
		String sql = "{ call add_room(?, ?, ?, ?, ?, ?) }";
		stmt = getConnection().prepareCall(sql);
		stmt.setInt(1, room.getId());
		switch (room.getRoomType()) {
			case SIMPLE:
				stmt.setString(2, "simple");
				break;
			case DOUBLE:
				stmt.setString(2, "double");
				break;
			case SUITE:
				stmt.setString(2, "suite");
				break;
		}
		stmt.setInt(3, room.getFloor());
		stmt.setInt(4, room.getCapacity());
		stmt.setFloat(5, room.getPrice());
		switch (room.getState()) {
			case LIBRE:
				stmt.setInt(6, 0); // 0 for LIBRE
				break;
			case OCCUPEE:
				stmt.setInt(6, 1); // 1 for OCCUPEE
				break;
			case MAINTENANCE:
				stmt.setInt(6, 2); // 2 for MAINTENANCE
				break;
		}
		return stmt;
	}

	@Override
	protected void insertInputValidation(Room room) throws ControllerException {
		ArrayList<Room> rooms = getData();
		for (Room r : rooms) {
			if (r.getId() == room.getId()) {
				throw new ControllerException("L'identifiant existe deja.");
			}
		}

		if (room.getId() <= 0) {
			throw new ControllerException("L'identifiant doit etre superieur a zero.");
		}

		if (room.getCapacity() <= 0) {
			throw new ControllerException("La capacité ne doit pas etre nulle.");
		}

		if (room.getPrice() <= 0) {
			throw new ControllerException("Le prix ne doit pas etre nul.");
		}

		if (room.getFloor() < 0) {
			throw new ControllerException("L'étage ne doit pas etre nul.");
		}

		if (room.getRoomType() == null) {
			throw new ControllerException("Le type de chambre ne doit pas etre nul.");
		}

		if (room.getState() == null) {
			throw new ControllerException("L'état de la chambre ne doit pas etre nul.");
		}
	}

	@Override
	protected String getDeleteRequest() {
		return "{ call delete_room(?) }";
	}

	@Override
	protected CallableStatement getUpdateStatement(Room data) throws SQLException {
		String sql = "{ call modify_room(?, ?, ?, ?) }";
		CallableStatement stmt = getConnection().prepareCall(sql);
		stmt.setInt(1, data.getId());
		stmt.setInt(2, data.getCapacity());
		stmt.setFloat(3, data.getPrice());
		switch (data.getState()) {
			case LIBRE:
				stmt.setInt(4, 0); // 0 for LIBRE
				break;
			case OCCUPEE:
				stmt.setInt(4, 1); // 1 for OCCUPEE
				break;
			case MAINTENANCE:
				stmt.setInt(4, 2); // 2 for MAINTENANCE
				break;
		}

		return stmt;
	}

	@Override
	protected void updateInputValidation(Room data) throws ControllerException {

		if (data.getCapacity() <= 0) {
			throw new ControllerException("La capacité ne doit pas etre nulle.");
		}

		if (data.getPrice() <= 0) {
			throw new ControllerException("Le prix ne doit pas etre nul.");
		}

		if (data.getState() == null) {
			throw new ControllerException("L'état de la chambre ne doit pas etre nul.");
		}
	}
}
