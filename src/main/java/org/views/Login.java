package org.views;

import org.controllers.Controller;
import org.controllers.exceptions.ControllerException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Login {

	@FXML
	private TextField email;

	@FXML
	private PasswordField password;

	@FXML
	private Label error;

	@FXML
	private Label signup;

	@FXML
	private Button submit;

	@FXML
	void loginPressed(ActionEvent event) throws Exception {

		String emailText = email.getText(); // Récupérer le texte du champ email
		String passwordText = password.getText(); // Récupérer le texte du champ mot de passe
		App main = new App(); // Créer une nouvelle instance de App pour changer la scène

		// Effacer les messages d'erreur précédents
		email.getStyleClass().remove("text-field-error"); 
		password.getStyleClass().remove("text-field-error");

		try {
			switch (Controller.getInstance().getUserManager().checkLogin(emailText, passwordText)) {
				case NORMAL_USER:
					System.out.println("Login successful! normal user");
					((MainMenu) main.changeScene("/mainMenu.fxml")).setNonAdmin();
					break;
				case ADMIN_USER:
					System.out.println("Login successful! admin user");
					main.changeScene("/mainMenu.fxml");
					break;
				case USER_NOT_FOUND:
					System.out.println("Login failed. User not found.");
					error.setText("Email ou mot de passe incorrecte.");
					// Ajout de la classe d'erreur pour le champ email et mot de passe
					email.getStyleClass().add("text-field-error");
					password.getStyleClass().add("text-field-error");
					break;
				case INACTIVE_USER:
					System.out.println("Login failed. User is inactive.");
					main.changeScene("/confirmationPending.fxml");
					break;
				default:
					break;
			}
		} catch (ControllerException exception) {
			error.setText(exception.toString());
			if (exception.getMessage().contains("email")) {
				// Ajout de la classe d'erreur pour le champ email
				email.getStyleClass().add("text-field-error");
			}
			if (exception.getMessage().contains("mot de passe")) {
				// Ajout de la classe d'erreur pour le champ mot de passe
				password.getStyleClass().add("text-field-error");
			}
		}
	}

	@FXML
	void loadSignup(MouseEvent event) throws Exception {
		App main = new App(); // Créer une nouvelle instance de App pour changer la scène
		main.changeScene("/signup.fxml");
	}
}
