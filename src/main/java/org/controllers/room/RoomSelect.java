package org.controllers.room;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import java.util.ArrayList;

import org.controllers.DBLoader;
import org.database.DBConnect;
import org.models.Room;
import org.models.Room.RoomState;
import org.models.Room.RoomType;

public class RoomSelect extends DBLoader {
	private ArrayList<Room> data;

	public RoomSelect() {
		data = new ArrayList<>();
		load();
	}
	
	public ArrayList<Room> getData() {
		return data;
	}

	public void load() {
		data = dataFromDB();
	}

	public static ArrayList<Room> dataFromDB() {
		Connection connection = null;
		CallableStatement stmt = null;
		ArrayList<Room> rooms = new ArrayList<>();
		try {
			connection = DBConnect.connect();

			// Vérification de la connexion
			if (connection == null) {
				System.err.println("Échec de la connexion à la base de données.");
				return null; // Indique que la connexion a échoué
			}

			String sql = "{ call get_all_rooms(?) }";
			stmt = connection.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);

			stmt.execute();
			ResultSet result = null;
			try {
				result = (ResultSet) stmt.getObject(1);
				while (result.next()) {
					int id = result.getInt("id_chambre");
					RoomType roomType;
					switch (result.getString("type_chambre")) {
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
							roomType = RoomType.SIMPLE; // Valeur par défaut si le type
											// n'est pas reconnu
							break;
					}
					int floor = result.getInt("etage");
					int numberOfPeople = result.getInt("nb_personnes");
					float price = result.getFloat("prix");
					RoomState state;
					switch (result.getInt("etat")) {
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
							state = RoomState.LIBRE; // Valeur par défaut si l'état n'est
											// pas reconnu
							break;
					}
					rooms.add(new Room(id, roomType, floor, numberOfPeople, price, state));
				}
			} finally {
				if (result != null) {
					try {
						result.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		} finally {
			// toujour executer le bloc finally
			// Fermeture des ressources JDBC
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return rooms;
	}
	
	// public static void main(String[] args) {
	// if (roomSelect() != null) {
	// ArrayList<RoomModel> rooms = roomSelect();
	// for (int i = 0; i < rooms.size(); i++) {
	// System.out.println("ID: " + rooms.get(i).getIdChambre() + ", Type: " +
	// rooms.get(i).getTypeChambre() +
	// ", Etage: " + rooms.get(i).getEtage() + ", Nombre de personnes: " +
	// rooms.get(i).getNbPersonnes() +
	// ", Prix: " + rooms.get(i).getPrix() + ", Etat: " + rooms.get(i).getEtat());
	// }
	// }
	// }
}
