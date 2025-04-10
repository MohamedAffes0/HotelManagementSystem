package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import org.app.LoginChecker;

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
	
        if (!isEmailValid(emailText)) {
                error.setText("Email invalide.");
                return;
        }

        switch (LoginChecker.loginCheck(emailText, passwordText)) {
            case NORMAL_USER:
                System.out.println("Login successful! normal user");
                main.changeScene("/mainMenu.fxml");
                main.isAdminUser = false;
                break;
            case ADMIN_USER:
                System.out.println("Login successful! admin user");
                main.changeScene("/mainMenu.fxml");
                main.isAdminUser = true;
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

    boolean isEmailValid(String emailText) {
        int altIndex = emailText.indexOf("@");
        
        if (altIndex == -1) {
            return false;
        }
        
        int dotIndex = emailText.lastIndexOf(".");

        if (dotIndex == -1) {
            return false;
        }
        
        // Si il n'y a pas de . apres le @
        if (altIndex > dotIndex) {
            return false;
        }
        
        return true;
    }
    @FXML
    void loadSignup(MouseEvent event) throws Exception {
        App main = new App();
        main.changeScene("/signup.fxml");
    }
}
