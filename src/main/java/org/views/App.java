package org.views;

import java.util.Locale;

import org.controllers.Controller;
import org.controllers.FileManager;
import org.controllers.FileManager.Config;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Stage stage; // Pour changer la scène de l'application duarant l'execution d'autre fichiers FXML

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Définir les paramètres régionaux par défaut sur le français
		Locale.setDefault(Locale.FRANCE);

		stage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

		var scene = new Scene(root, 1280, 720);

		stage.setTitle("Emerald Coast Dashboard");
		stage.setScene(scene);
		Application.setUserAgentStylesheet("style.css");
		stage.show();
	}

	// Méthode pour changer la scène de l'application
	public Object changeScene(String file) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
		Parent root = loader.load();
		Scene scene = stage.getScene();
		scene.setRoot(root);
		return loader.getController();
	}

	public static void main(String[] args) {

		// Récupérer la configuration de la base de données à partir du fichier config.json
		Config config = FileManager.getConfig();

		try {
			// Vérifier si la configuration est valide
			Controller.getInstance().initializeConnection(config.url, config.user, config.password);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}
		
		launch();
	}
}
