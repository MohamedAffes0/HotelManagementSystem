package org.openjfx;

import org.app.EmailChecker;
import org.app.auth.LoginChecker;

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
    void loginPressed(ActionEvent event) throws Exception{
        String emailText = email.getText();
        String passwordText = password.getText();
        App main = new App();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            System.out.println("Please fill in all fields.");
            error.setText("Veuillez saisir votre email et votre mot de passe.");
            return;
        }
	
        if (!EmailChecker.isValid(emailText)) {
                error.setText("Email invalide.");
                return;
        }

        switch (LoginChecker.loginCheck(emailText, passwordText)) {
            case NORMAL_USER:
                System.out.println("Login successful! normal user");
                main.changeScene("/mainMenu.fxml");
                App.isAdminUser = false;
                break;
            case ADMIN_USER:
                System.out.println("Login successful! admin user");
                main.changeScene("/mainMenu.fxml");
                App.isAdminUser = true;
                break;
            case USER_NOT_FOUND:
                System.out.println("Login failed. User not found.");
                error.setText("Email incorrecte.");
                break;
            case INACTIVE_USER:
                System.out.println("Login failed. User is inactive.");
                main.changeScene("/confirmationPending.fxml");
                break;
            case CONNEXION_FAILED:
                System.out.println("Login failed. Connection error.");
                error.setText("Erreur de connexion.");
                break;
            default:
                System.out.println("Unknown error.");
                error.setText("Erreur.");
                break;
        }
    }


    @FXML
    void loadSignup(MouseEvent event) throws Exception {
        App main = new App();
        main.changeScene("/signup.fxml");
    }
}
