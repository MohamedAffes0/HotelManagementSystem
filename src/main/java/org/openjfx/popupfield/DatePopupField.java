package org.openjfx.popupfield;

import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

public class DatePopupField extends PopupField {

	private DatePicker datePicker;
	private Button calendarButton;

	public DatePopupField(String name) {
		super(name);

		// Crée et configure le DatePicker
		datePicker = new DatePicker();
		// datePicker.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		// datePicker.setPrefWidth(100);

		// Crée le bouton calendrier
		calendarButton = new Button("📅");
		calendarButton.setStyle(
			"-fx-background-color: transparent; " +
			"-fx-text-fill: white; " +
			"-fx-font-size: 10px; " +
			"-fx-background-radius: 2; " +
			"-fx-cursor: hand;" +
			"-fx-padding: 5; "
		);
		calendarButton.setOnAction(e -> datePicker.show());

		// Assemble les deux dans un HBox avec le bouton à droite
		HBox fieldWithButton = new HBox(1, calendarButton, datePicker);
		fieldWithButton.setStyle("-fx-alignment: center-left;");

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

