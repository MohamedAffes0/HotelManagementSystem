package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.app.UserAdd;

public class Signup {

    @FXML
    private Button confirm;

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
    void submit(ActionEvent event) {
        String nomText = nom.getText();
        String prenomText = prenom.getText();
        String emailText = email.getText();
        String passwordText = password.getText();

        // Appel de la méthode userAdd avec les valeurs des champs de texte
        boolean result = UserAdd.userAdd(nomText, prenomText, emailText, passwordText, false, false);

        if (result) {
            System.out.println("Utilisateur ajouté avec succès !");
        } else {
            System.out.println("Échec de l'ajout de l'utilisateur.");
        }
    }

}

