package org.controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileManager {

    public static class Config {
		public String url;
		public String user;
		public String password;

        // Default constructor with default values
		public Config() {
			this.url = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
			this.user = "hotel_user";
			this.password = "2426";
		}
	}

    public static Config getConfig() {
        Gson gson = new Gson();
		String dataFolder = System.getenv("APPDATA") + "\\dbconfig.json"; // retourne le chemin du dossier AppData de l'utilisateur courant
        try (FileReader reader = new FileReader(dataFolder)) {
            Config data = gson.fromJson(reader, Config.class); // lit le fichier JSON et le convertit en objet Config
            return data;
        } catch (IOException exception) {
            System.out.println("Error reading config file: " + exception.getMessage());
			Config data = new Config();
			Gson builder = new GsonBuilder().setPrettyPrinting().create(); // crée un objet Gson pour écrire le fichier JSON
            String json = builder.toJson(data);
			try (FileWriter writer = new FileWriter(dataFolder)) {
                writer.write(json); // écrit l'objet Config dans le fichier JSON
                return data;
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
                return new Config();
            }
        }
    }
}
