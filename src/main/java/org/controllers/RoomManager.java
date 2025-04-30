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

	// retourne les 10% des chambres les plus visitées
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
			select(); // Remplir la liste des chambres si elle est vide
		}

		// Calculer le nombre total de réservations
		for (Room room : getData()) {
			Integer count = reservationCount.get(room.getId());
			if (count != null) {
				totalReservations += count;
			}
		}

		double threshold = totalReservations * 0.1; // 10% des réservations

		// Parcourir la liste des chambres et ajouter celles qui dépassent le seuil
		for (Room room : getData()) {
			int roomId = room.getId();
			if (reservationCount.containsKey(roomId) && reservationCount.get(roomId) >= threshold) {
				mostCovetedRooms.add(room);
			}
		}

		return mostCovetedRooms;
	}

	// retourne une liste de chambres disponibles entre deux dates
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

		// Vérifier si les dates de début et de fin sont nulles
		if (startDate == null || endDate == null) {
			new ControllerException("Les dates de début et de fin ne doivent pas être nulles.");
		}

		// vérifier si la date de début est après la date de fin
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
				availableRooms.add(roomId); // Ajouter l'ID de la chambre à la liste des chambres disponibles
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

	// retourne une hashmap contenant le nombre de reservations par chambre
	/**
	 * Calculates the number of times each room was reserved.
	 * 
	 * @return HashMap<roomId, number of reservations>
	 * @throws DBException
	 */
	private HashMap<Integer, Integer> reservationCount() throws DBException {
		ArrayList<Reservation> reservations = Controller.getInstance().getReservationManager().getData();

		// Vérifier si la liste des réservations est vide
		if (reservations.isEmpty()) {
			Controller.getInstance().getReservationManager().select();
			reservations = Controller.getInstance().getReservationManager().getData();
		}

		HashMap<Integer, Integer> reservationCount = new HashMap<>();

		// Parcourir la liste des réservations et compter le nombre de réservations par chambre
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

	// retourner vrai si la chambre correspond au critère de recherche
	@Override
	public boolean filter(Room room, String criterea, String searchText) {
		if (searchText.isEmpty()) {
			return true;
		}
		// La chaîne n'est pas vide, donc vérifier le type de filtre
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
				// Retourner vrai si searchText = "s" car "simple" et "suite" commencent tous deux par "s"
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

	// retourne l'état de la chambre
	private RoomState getRoomState(int roomId) throws SQLException {

		String sql = "{ ? = call get_room_state(?) }";
		CallableStatement stmt = null;
		stmt = Controller.getInstance().getConnection().prepareCall(sql);
		stmt.registerOutParameter(1, OracleTypes.INTEGER);
		stmt.setInt(2, roomId);
		stmt.execute();

		int resultSet = (int) stmt.getObject(1);
		stmt.close();
		switch (resultSet) {
			case 0:
				return RoomState.LIBRE;
			case 1:
				return RoomState.OCCUPEE;
			case 2:
				return RoomState.MAINTENANCE;
			default:
				return RoomState.LIBRE;
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
		RoomState state = getRoomState(id);

		return new Room(id, roomType, floor, numberOfPeople, price, state);
	}

	@Override
	protected String getSelectRequest() {
		return "{ call get_all_rooms(?) }";
	}

	@Override
	protected CallableStatement getInsertStatement(Room room) throws SQLException {
		CallableStatement stmt;
		String sql = "{ call add_room(?, ?, ?, ?, ?) }";
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

		return stmt;
	}

	@Override
	protected void insertInputValidation(Room room) throws ControllerException {
		ArrayList<Room> rooms = getData();

		// Vérifier l'existence de l'identifiant
		for (Room r : rooms) {
			if (r.getId() == room.getId()) {
				throw new ControllerException("L'identifiant existe deja.");
			}
		}

		// verifier la validation des champs
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

	}

	@Override
	protected String getDeleteRequest() {
		return "{ call delete_room(?) }";
	}

	@Override
	protected CallableStatement getUpdateStatement(Room data) throws SQLException {
		String sql = "{ call modify_room(?, ?, ?) }";
		CallableStatement stmt = getConnection().prepareCall(sql);
		stmt.setInt(1, data.getId());
		stmt.setInt(2, data.getCapacity());
		stmt.setFloat(3, data.getPrice());

		return stmt;
	}

	@Override
	protected void updateInputValidation(Room data) throws ControllerException {

		// verifier la validation des champs
		if (data.getCapacity() <= 0) {
			throw new ControllerException("La capacité ne doit pas etre nulle.");
		}

		if (data.getPrice() <= 0) {
			throw new ControllerException("Le prix ne doit pas etre nul.");
		}

	}
}
