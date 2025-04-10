package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import org.app.UserAdd;
import org.app.UserAdd.CreationStatus;

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
    void loadLogin(MouseEvent event) throws Exception{
	App main = new App();
	main.changeScene("/login.fxml");

    }

    @FXML
    void submit(ActionEvent event) throws Exception{
        String nomText = nom.getText();
        String prenomText = prenom.getText();
        String emailText = email.getText();
        String passwordText = password.getText();
	
	// Test if fields are empty
	if (nomText.isEmpty() || prenomText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
        error.setText("Veuillez remplir les champs!");
        return;
	}
        // Appel de la méthode userAdd avec les valeurs des champs de texte
        CreationStatus result = UserAdd.userAdd(nomText, prenomText, emailText, passwordText, false, false);

        if (result == CreationStatus.SUCCESS) {
            System.out.println("Utilisateur ajouté avec succès !");
            App main = new App();
            main.changeScene("/mainMenu.fxml");
        } else {
            System.out.println("Échec de l'ajout de l'utilisateur.");
            error.setText("Verifier la connexion ou si vous avez deja un compte!");
        }
    }

}

