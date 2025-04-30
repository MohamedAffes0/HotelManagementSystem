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

		public Config() {
			this.url = "jdbc:oracle:thin:@localhost:1521/ORCLPDB";
			this.user = "hotel_user";
			this.password = "2426";
		}
	}

    public static Config getConfig() {
        Gson gson = new Gson();
		String dataFolder = System.getenv("APPDATA") + "\\dbconfig.json";
        try (FileReader reader = new FileReader(dataFolder)) {
            Config data = gson.fromJson(reader, Config.class);
            return data;
        } catch (IOException exception) {
            System.out.println("Error reading config file: " + exception.getMessage());
			Config data = new Config();
			Gson builder = new GsonBuilder().setPrettyPrinting().create();
            String json = builder.toJson(data);
			try (FileWriter writer = new FileWriter(dataFolder)) {
                writer.write(json);
                return data;
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
                return new Config();
            }
        }
    }
}
