package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import org.app.EmailChecker;
import org.app.user.UserAdd;
import org.app.user.UserSelect;
import org.app.user.UserAdd.CreationStatus;
import org.models.Employee;

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
	void loadLogin(MouseEvent event) throws Exception {
		App main = new App();
		main.changeScene("/login.fxml");

	}

	@FXML
	void submit(ActionEvent event) throws Exception {
		String nameText = nom.getText();
		String lastNameText = prenom.getText();
		String emailText = email.getText();
		String passwordText = password.getText();

		// Test if fields are empty
		if (nameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
			error.setText("Veuillez remplir les champs!");
			return;
		}

		if (EmailChecker.isValid(emailText)) {
			error.setText("Email Invalide");
		}
		// Appel de la méthode userAdd avec les valeurs des champs de texte
		Employee user = new Employee(1, nameText, lastNameText, emailText, passwordText, false,
				false);
		CreationStatus result = UserAdd.userAdd(user, UserSelect.dataFromDB());

		if (result == CreationStatus.SUCCESS) {
			System.out.println("Utilisateur ajouté avec succès !");
			App main = new App();
			main.changeScene("/confirmationPending.fxml");
		} else {
			System.out.println("Échec de l'ajout de l'utilisateur.");
			error.setText("Verifier la connexion ou si vous avez deja un compte!");
		}
	}

}
