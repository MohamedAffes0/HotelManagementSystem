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
		// if (args.length != 3) {
		// System.out.println("Usage: java -jar app.jar <url> <user> <password>");
		// return;
		// }

		// String url = args[0];
		// String user = args[1];
		// String password = args[2];

		try {
			Controller.getInstance().initializeConnection("jdbc:oracle:thin:@localhost:1521/ORCLPDB",
					"hotel_user", "2426");

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}
		Application.setUserAgentStylesheet("style.css");
		launch();
	}
}
