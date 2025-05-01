package org.controllers;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Person;
import org.models.Reservation;

public class ClientManager extends Manager<Person> {
	public ClientManager() {
		super();
	}

	// retourner le nombre de réservations par client
	public static HashMap<Integer, Integer> clientReservationCount() throws DBException {
		ArrayList<Reservation> reservations = Controller.getInstance().getReservationManager().getData();

		// Si la liste des réservations est vide, on appelle la méthode select() pour remplir la liste
		if (reservations.isEmpty()) {
			Controller.getInstance().getReservationManager().select();
			reservations = Controller.getInstance().getReservationManager().getData();
		}

		HashMap<Integer, Integer> reservationCount = new HashMap<>();

		// Parcourir la liste des réservations et compter le nombre de réservations par client
		for (int i = 0; i < reservations.size(); i++) {

			if (reservations.get(i).getHotelClient() == null) {
				continue; // Passer si le client de l'hôtel est nul
			}

			int currentClient = reservations.get(i).getHotelClient();
			if (reservationCount.containsKey(currentClient)) {
				reservationCount.replace(currentClient, reservationCount.get(currentClient) + 1); // Incrémenter le compteur
			} else {
				reservationCount.put(currentClient, 1); // Initialiser le compteur à 1
			}
		}

		return reservationCount;
	}

	// retourne les 10% des clients les plus fidèles
	/*
	 * Returns the top 10% most faithful clients. `clients` is an `ArrayList`
	 * containing all the clients.
	 */
	public ArrayList<Person> getFaithfulClients() throws DBException {
		ArrayList<Person> clients = getData();
		if (clients.isEmpty()) {
			select(); // Remplir la liste des clients si elle est vide
		}

		HashMap<Integer, Integer> reservationCount = clientReservationCount();
		ArrayList<Person> faithfulClients = new ArrayList<>();
		int totalReservations = 0;
		for (int i = 0; i < clients.size(); i++) {
			Integer count = reservationCount.get(clients.get(i).getCin());
			if (count != null) {
				totalReservations += count; // Compter le nombre total de réservations
			}
		}

		double threshold = totalReservations * 0.1; // 10% of total reservations

		// Parcourir la liste des clients et ajouter ceux qui ont un nombre de reservations superieur au seuil calculé
		for (int i = 0; i < clients.size(); i++) {
			int clientId = clients.get(i).getCin();
			if (reservationCount.containsKey(clientId) && reservationCount.get(clientId) >= threshold) {
				faithfulClients.add(clients.get(i)); // Ajouter le client à la liste des clients fidèles
			}
		}

		return faithfulClients;
	}

	@Override
	public boolean filter(Person client, String criterea, String searchText) {
		if (searchText.isEmpty()) {
			return true;
		}

		// La chaîne n'est pas vide, donc vérifier le type de filtre
		switch (criterea) {
			case "Cin":
				String cin = String.valueOf(client.getCin());
				return cin.contains(StringNumberExtract.extract(searchText)); // Vérifier si le CIN contient la chaîne de recherche
			default:
				return true;
		}
	}

	@Override
	protected Person dataFromResultSet(ResultSet resultSet) throws SQLException {
		int cin = resultSet.getInt("cin");
		String name = resultSet.getString("nom");
		String lastName = resultSet.getString("prenom");
		String mail = resultSet.getString("mail");
		return new Person(cin, name, lastName, mail);
	}

	@Override
	protected String getSelectRequest() {
		return "{ call get_all_clients(?) }";
	}

	@Override
	protected CallableStatement getInsertStatement(Person data) throws SQLException {
		CallableStatement stmt;
		String sql = "{ call add_client_hotel(?, ?, ?, ?) }";
		stmt = getConnection().prepareCall(sql);
		stmt.setInt(1, data.getCin());
		stmt.setString(2, data.getName());
		stmt.setString(3, data.getLastName());
		stmt.setString(4, data.getMail());
		return stmt;
	}

	@Override
	protected void insertInputValidation(Person data) throws ControllerException {

		ArrayList<Person> clients = getData();

		// Vérifier l'existence
		for (Person client : clients) {
			if (client.getCin() == data.getCin()) {
				throw new ControllerException("Ce CIN existe deja.");
			}
		}

		// Verification des champs vides
		if (data.getMail() == null || data.getMail().isEmpty()) {
			throw new ControllerException("L'e-mail est obligatoire.");
		}

		if (data.getName() == null || data.getName().isEmpty()
				|| data.getLastName() == null || data.getLastName().isEmpty()) {
			throw new ControllerException("Veuillez saisir nom et un prenom.");
		}

		// Verifier si l'email est valide
		if (!EmailChecker.isValid(data.getMail())) {
			throw new ControllerException("L'e-mail est invalide.");
		}

		// Vérifier le CIN
		if (data.getCin() < 1 || data.getCin() > 99999999) {
			throw new ControllerException("CIN invalide.");
		}

	}

	@Override
	protected String getDeleteRequest() {
		return "{ call delete_client(?) }";
	}

	@Override
	protected CallableStatement getUpdateStatement(Person data) throws SQLException {
		CallableStatement statement;
		String sql = "{ call modify_client(?, ?, ?, ?) }";
		statement = getConnection().prepareCall(sql);
		statement.setInt(1, data.getCin());
		statement.setString(2, data.getName());
		statement.setString(3, data.getLastName());
		statement.setString(4, data.getMail());
		return statement;
	}

	@Override
	protected void updateInputValidation(Person data) throws ControllerException {

		// Verification des champs vides
		if (data.getMail() == null || data.getMail().isEmpty()) {
			throw new ControllerException("L'e-mail est obligatoire.");
		}

		if (data.getName() == null || data.getName().isEmpty()
				|| data.getLastName() == null || data.getLastName().isEmpty()) {
			throw new ControllerException("Veuillez saisir nom et un prenom.");
		}

		// Verifier si l'email est valide
		if (!EmailChecker.isValid(data.getMail())) {
			throw new ControllerException("L'e-mail est invalide.");
		}
	}
}
