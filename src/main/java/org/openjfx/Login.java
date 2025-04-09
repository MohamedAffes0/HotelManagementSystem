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

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            System.out.println("Please fill in all fields.");
            error.setText("Please fill in all fields.");
            return;
        }
        switch (LoginChecker.loginCheck(emailText, passwordText)) {
            case NORMAL_USER:
                System.out.println("Login successful! normal user");
                error.setText("Login successful! normal user");
                break;
            case ADMIN_USER:
                System.out.println("Login successful! admin user");
                error.setText("Login successful! admin user");
                break;
            case USER_NOT_FOUND:
                System.out.println("Login failed. User not found.");
                error.setText("Login failed. User not found.");
                break;
            case INACTIVE_USER:
                System.out.println("Login failed. User is inactive.");
	        App main = new App();
	        main.changeScene("/confirmationPending.fxml");
                break;
            case CONNEXION_FAILED:
                System.out.println("Login failed. Connection error.");
                error.setText("Login failed. Connection error.");
                break;
            default:
                System.out.println("Unknown error.");
                error.setText("Unknown error.");
                break;
        }
    }

    @FXML
    void loadSignup(MouseEvent event) throws Exception {
	App main = new App();
	main.changeScene("/signup.fxml");
    }
}
