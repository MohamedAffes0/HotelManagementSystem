package org.openjfx.popupfield;

import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class DatePopupField extends PopupField {

    private DatePicker datePicker;
    private Button calendarButton;

    public DatePopupField(String name) {
        super(name);

        // Crée et configure le DatePicker
        datePicker = new DatePicker();
        datePicker.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        datePicker.setPrefWidth(200);  // Fixe la largeur du DatePicker

        // Crée le bouton calendrier
        calendarButton = new Button("📅");
        calendarButton.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 5px; "
        );
        calendarButton.setMaxWidth(30);  // Fixe la largeur du bouton à 30px

        // Action du bouton
        calendarButton.setOnAction(e -> datePicker.show());

        // Crée un HBox principal avec le DatePicker à gauche et le bouton à droite
        HBox fieldWithButton = new HBox(5, datePicker, calendarButton);
        fieldWithButton.setStyle("-fx-alignment: center-left;");

        // On contrôle que le DatePicker prenne toute la place restante
        HBox.setHgrow(datePicker, Priority.ALWAYS);  // Le DatePicker prend tout l'espace restant

        // Ajoute dans le container parent
        container.getChildren().add(fieldWithButton);

        // Important : pour que getField() retourne bien le DatePicker
        setField(datePicker);
    }

    @Override
    public LocalDate getValue() {
        return datePicker.getValue();
    }

    @Override
    public boolean isEmpty() {
        return getValue() == null;
    }
}



