package org.views.popupfield;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

public class DatePopupField extends PopupField<DatePicker, LocalDate> {

	private DatePicker datePicker;
	private Button calendarButton;

	public DatePopupField(String name) {
		super(name);

		// Crée et configure le DatePicker
		datePicker = new DatePicker();
		datePicker.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		datePicker.setPrefWidth(200); // Fixe la largeur du DatePicker

		// Crée le bouton calendrier
		calendarButton = new Button("");

		calendarButton.getStyleClass().add("calendar-button");
		calendarButton.setPrefWidth(30); // Limite la largeur du bouton à 30px

		// Action du bouton
		calendarButton.setOnAction(e -> datePicker.show());

		// Crée un HBox avec le DatePicker à gauche et le bouton à droite
		HBox fieldWithButton = new HBox(5, datePicker, calendarButton);
		fieldWithButton.setStyle("-fx-alignment: center_left;");
		fieldWithButton.setAlignment(Pos.CENTER_LEFT);

		// Important : pour que getField() retourne bien le DatePicker
		setField(datePicker);
		datePicker.setPrefWidth(170);

		// Ajoute le conteneur dans le parent
		container.getChildren().add(fieldWithButton);
	}

	public void setOnAction(EventHandler<ActionEvent> event) {
		datePicker.setOnAction(event);
	}

	@Override
	public LocalDate getValue() {
		return datePicker.getValue();
	}

	@Override
	public boolean isEmpty() {
		return getValue() == null;
	}

	@Override
	public void setValue(LocalDate value) {
		datePicker.setValue(value);
	}
}
