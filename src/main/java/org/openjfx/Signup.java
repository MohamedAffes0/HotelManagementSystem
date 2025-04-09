package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

    }

}

