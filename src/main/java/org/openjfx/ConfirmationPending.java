package org.openjfx;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class ConfirmationPending {
    @FXML
    void loadInitial(ActionEvent event) throws Exception{
        App main = new App();
        main.changeScene("/login.fxml");
    }
}

