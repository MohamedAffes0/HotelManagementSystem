package org.views;

import java.util.Locale;

import org.controllers.Controller;

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
	public static boolean isAdminUser = false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Used to fix date picker appearing in different languages.
		Locale.setDefault(Locale.FRANCE);
		
		stage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

		var scene = new Scene(root, 1280, 720);

		stage.setTitle("Emerald Coast Dashboard");
		stage.setScene(scene);
		stage.show();
	}

	public void changeScene(String file) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(file));
		Scene scene = stage.getScene();
		scene.setRoot(root);
	}

	public static void main(String[] args) {
		Controller.getInstance().initializeConnection(url, user, password);
		Application.setUserAgentStylesheet("style.css");
		launch();
	}
}
