package org.controllers;

import java.io.File;
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

        // Constructeur avec valeurs par défaut
        public Config() {
            this.url = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
            this.user = "hotel_user";
            this.password = "2426";
        }
    }

    // Méthode pour obtenir le chemin du fichier config selon OS
    private static String getConfigFilePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String homeDir = System.getProperty("user.home");
        if (os.contains("win")) {
            // Windows -> dossier APPDATA
            String appData = System.getenv("APPDATA");
            if (appData != null) {
                return appData + File.separator + "dbconfig.json";
            } else {
                // fallback si APPDATA non trouvé
                return homeDir + File.separator + "dbconfig.json";
            }
        } else {
            // Linux / macOS -> dossier utilisateur, fichier caché
            return homeDir + File.separator + ".dbconfig.json";
        }
    }

    public static Config getConfig() {
        Gson gson = new Gson();
        String configPath = getConfigFilePath();
        try (FileReader reader = new FileReader(configPath)) {
            Config data = gson.fromJson(reader, Config.class);
            return data;
        } catch (IOException exception) {
            System.out.println("Error reading config file: " + exception.getMessage());
            Config data = new Config();
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            String json = builder.toJson(data);
            try (FileWriter writer = new FileWriter(configPath)) {
                writer.write(json);
                return data;
            } catch (IOException e) {
                System.err.println("Error writing config file: " + e.getMessage());
                return new Config();
            }
        }
    }
}
