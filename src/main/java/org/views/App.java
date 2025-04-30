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
	private static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Used to fix date picker appearing in different languages.
		Locale.setDefault(Locale.FRANCE);

		stage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

		var scene = new Scene(root, 1280, 720);

		stage.setTitle("Emerald Coast Dashboard");
		stage.setScene(scene);
		Application.setUserAgentStylesheet("style.css");
		stage.show();
	}

	public Object changeScene(String file) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
		Parent root = loader.load();
		Scene scene = stage.getScene();
		scene.setRoot(root);
		return loader.getController();
		//Parent root = FXMLLoader.load(getClass().getResource(file));
		//Scene scene = stage.getScene();
		//scene.setRoot(root);
	}

	public static void main(String[] args) {

		Config config = FileManager.getConfig();

		try {
			Controller.getInstance().initializeConnection(config.url, config.user, config.password);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}
		
		launch();
	}
}
