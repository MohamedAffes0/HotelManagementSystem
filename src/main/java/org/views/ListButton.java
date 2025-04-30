package org.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import org.models.Model;
import org.models.ModelField;
import org.views.popup.UpdatePopup;

import javafx.scene.control.Button;

public class ListButton {
	Model data;
	UpdatePopup popup;

	@FXML
	public Button button;

	@FXML
	private HBox content;

	@FXML
	private Label id;

	public void setData(Model data) {
		if (data == null)
			throw new RuntimeException("Null model");

		this.data = data;

		// Définir le texte du bouton sur le premier champ du modèle
		ArrayList<ModelField> fields = data.getFields();

		// Vérifier si le modèle contient des champs
		if (fields == null || fields.isEmpty())
			throw new RuntimeException("No data in model");

		id.setText(fields.get(0).getContent()); // Le premier champ est le nom du modèle
		// ajouter une icône si elle existe
		if (fields.get(0).getIcon() != null) {
			id.setGraphic(fields.get(0).getIcon());
			id.setContentDisplay(fields.get(0).getIconPosition());
		}

		// Supprimer tous les champs sauf le premier
		content.getChildren().clear();

		// Ajouter tous les champs sauf le premier
		for (int i = 1; i < fields.size(); i++) {
			ModelField field = fields.get(i); // Le champ qui sera utilisé dans la popup
			Label label = new Label(field.getContent()); // Le nom du champ qui sera utilisé dans la popup

			// ajouter un style si il existe
			if (field.getStyleClass() != null)
				label.getStyleClass().add(field.getStyleClass());
			label.setStyle("-fx-font-weight: bold;");

			// ajouter une icône si elle existe
			if (field.getIcon() != null) {
				label.setGraphic(field.getIcon());
				label.setContentDisplay(field.getIconPosition());
			}

			content.getChildren().add(label); // Ajouter le champ au bouton
		}
	}

	public void setPopup(UpdatePopup popup) {
		this.popup = popup;
	}

	// Retourne l'index du bouton dans la liste
	public int getIndex() {
		VBox parent = (VBox) button.getParent();
		return parent.getChildren().indexOf(button);
	}

	// Retourne vrai si le bouton est le premier dans la liste.
	public boolean isFirst() {
		return getIndex() == 0;
	}

	// Retourne vrai si le bouton est le dernier dans la liste.
	public boolean isLast() {
		VBox parent = (VBox) button.getParent();
		return getIndex() == (parent.getChildren().size() - 1);
	}

	@FXML
	// Appelé lorsque le bouton est pressé
	void pressed(ActionEvent event) {
		if (popup == null) {
			return;
		}
		try {
			popup.setData(data); // passer les données au popup
			// Charger la fenêtre contextuelle
			popup.load("Modifier", () -> {
				if (popup.getData() != null) {
					setData(popup.getData());
					return;
				}
				VBox parent = (VBox) button.getParent();
				int index = getIndex();

				// Supprimer le bouton
				parent.getChildren().remove(index);

				// Ne rien faire s'il n'y a pas de boutons
				if (parent.getChildren().size() == 0) {
					return;
				}

				// on modifie le style du bouton précédent s'il n'est pas le premier
				if (index > 0) {
					updateStyle((Button) parent.getChildren().get(index - 1));
					return;
				}

				// Modifier le bouton suivant s'il n'est pas le dernier
				if (index < parent.getChildren().size()) {
					updateStyle((Button) parent.getChildren().get(index));
				}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Error opening add popup");
		}
	}

	// Modifie le rayon de bordure du bouton s'il est en haut ou en bas de la liste.
	public static void updateStyle(Button button) {
		VBox parent = (VBox) button.getParent();
		int index = parent.getChildren().indexOf(button); // L'index du bouton dans la liste
		int listSize = parent.getChildren().size(); // La taille de la liste

		// Il n'y a qu'un seul bouton
		if (listSize == 1) {
			button.setStyle("-fx-background-radius: 12;");
			return;
		}

		// Le bouton est en haut
		if (index == 0) {
			button.setStyle("-fx-background-radius: 12 12 0 0;");
			return;
		}

		// Le bouton est en bas
		if (index == (listSize - 1)) {
			button.setStyle("-fx-background-radius: 0 0 12 12;");
			return;
		}

		// Le bouton est au milieu (ni en haut ni en bas)
		button.setStyle("-fx-background-radius: 0;");
	}
}
