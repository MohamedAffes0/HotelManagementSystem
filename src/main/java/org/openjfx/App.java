package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
	stage = primaryStage;
	VBox root = FXMLLoader.load(getClass().getResource("/signup.fxml"));

        var scene = new Scene(root, 640, 400);
	
	stage.setTitle("Hotel Management Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
	Application.setUserAgentStylesheet("nord-dark.css");
        launch();
    }

}
