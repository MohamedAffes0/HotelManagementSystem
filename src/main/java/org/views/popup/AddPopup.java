package org.views.popup;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import org.models.Model;
import org.views.popupfield.PopupField;

/**
 * AddPupup
 */
public abstract class AddPopup extends Popup {

	public AddPopup(PopupField<?, ?>... fields) {
		super();
		setFields(fields); // Définir les champs de la fenêtre contextuelle.

		setSuggestedText("Confirmer"); // Définir le texte du bouton de suggestion.
		setCancelText("Annuler"); // Définir le texte du bouton d'annulation.
	}

	// Méthode abstraite pour ajouter des données depuis le bouton de la liste pressé.
	protected abstract void addData(Model newData) throws Exception;

	// Méthode abstraite pour obtenir les données à partir des champs de saisie.
	protected abstract Model dataFromFields();

	@Override
	// Méthode appelée lorsque la fenêtre contextuelle est initialisée.
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		try {
			removeDestructive();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	// Appelé lorsque le bouton de mise à jour est pressé.
	public void suggestedPressed(ActionEvent event) {
		try {
			addData(dataFromFields()); // Ajouter les données à partir des champs de saisie.
			close(); // Fermer la fenêtre contextuelle.
		} catch (Exception exception) {
			exception.printStackTrace();
			setErrorMessage(exception.toString());
		}
	}
}
