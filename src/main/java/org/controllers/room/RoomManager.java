package org.controllers.room;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.controllers.exceptions.ControllerException;
import org.controllers.Controller;
import org.controllers.Manager;
import org.models.Room;
import org.models.Room.RoomState;
import org.models.Room.RoomType;

/**
 * UserManager
 */
public class RoomManager extends Manager<Room> {

	public RoomManager() {
		super();
	}

	/**
	 * Calculates the number of times each room was reserved.
	 * 
	 * @return HashMap<roomId, number of reservations>
	 */
	private HashMap<Integer, Integer> reservationCount() {
		ArrayList<Reservation> reservations = Controller.getInstance().getReservationManager().getData();

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

	/**
	 * Returns an Array list containing the top 10% most visited rooms.
	 * 
	 * @return
	 */
	public ArrayList<Room> getMostCovetedRooms() {
		HashMap<Integer, Integer> reservationCount = reservationCount();
		ArrayList<Room> mostCovetedRooms = new ArrayList<>();
		int totalReservations = 0;
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
		return "{ call get_all_users(?) }";
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
