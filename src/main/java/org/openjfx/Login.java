package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.app.LoginChecker;
public class Login {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label signup;

    @FXML
    private Button submit;

    @FXML
    void loginPressed(ActionEvent event) {
        String emailText = email.getText();
        String passwordText = password.getText();

        if (LoginChecker.loginCheck(emailText, passwordText)) {
            System.out.println("Login successful!");
            // Load the next scene or perform the next action
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

}
