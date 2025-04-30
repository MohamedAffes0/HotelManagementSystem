package org.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import org.models.Employee;
import org.controllers.Controller;

public class Signup {

	@FXML
	private Button confirm;

	@FXML
	private Label error;

	@FXML
	private TextField email;

	@FXML
	private TextField nom;

	@FXML
	private PasswordField password;

	@FXML
	private TextField prenom;

	@FXML
	// si le bouton "submit" est pressé, chargez le fichier FXML pour la page de connexion
	void loadLogin(MouseEvent event) throws Exception {
		App main = new App();
		main.changeScene("/login.fxml");

	}

	@FXML
	// si le bouton "submit" est pressé, appelez la méthode submit
	void submit(ActionEvent event) throws Exception {
		String nameText = nom.getText();
		String lastNameText = prenom.getText();
		String emailText = email.getText().trim();
		String passwordText = password.getText().trim();

		// Appel de la méthode userAdd avec les valeurs des champs de texte
		Employee user = new Employee(1, nameText, lastNameText, emailText, passwordText, false,
				false);
		
		try {
			Controller.getInstance().getUserManager().insert(user);
			App main = new App();
            main.changeScene("/confirmationPending.fxml"); // Changer la scène vers la page de confirmation
		} catch (Exception exception) {
			// Afficher le message d'erreur dans le label d'erreur
			error.setText(exception.toString());
		}
	}
}
