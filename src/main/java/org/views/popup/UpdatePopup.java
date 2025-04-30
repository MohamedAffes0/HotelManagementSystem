package org.views.popup;

import org.controllers.exceptions.ControllerException;
import org.models.Model;
import org.views.popupfield.PopupField;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.event.ActionEvent;

/**
 * UpdatePopup
 */
public abstract class UpdatePopup extends Popup {

	private Model data;

	public UpdatePopup(PopupField... fields) {

		super();

		// Définir les champs de la fenêtre contextuelle a partir de la bouton de la liste pressé.
		setFields(fields); 

		setSuggestedText("Modifier"); // Définir le texte du bouton de suggestion.
		setDestructiveText("Supprimer"); // Définir le texte du bouton de suppression.
		setCancelText("Annuler"); // Définir le texte du bouton d'annulation.
	}

	// Méthode abstraite pour mettre à jour les données depuis le bouton de la liste pressé.
	public abstract void update(Model newData) throws ControllerException;

	// Méthode abstraite pour supprimer les données depuis le bouton de la liste pressé.
	public abstract void delete();

	// Méthode abstraite pour obtenir les données à partir des champs de saisie.
	public abstract void fieldsFromData();

	// Méthode abstraite pour obtenir les données à partir des champs de saisie.
	protected abstract Model dataFromFields();

	public void setData(Model data) {
		this.data = data; // Mettre à jour les données de la fenêtre contextuelle.
	}

	public Model getData() {
		return data;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources); // Initialiser la fenêtre contextuelle.
		fieldsFromData(); // Remplir les champs de la fenêtre contextuelle avec les données.
	}

	@Override
	// Appelé lorsque le bouton de mise à jour est pressé.
	public void suggestedPressed(ActionEvent event) {
		try {
			Model data = dataFromFields();
			
			update(data); // La mise à jour peut générer une exception

			setData(data); // Si la mise à jour est réussie, alors nous définissons les données
			
			
			close(); // Fermer la fenêtre uniquement si nous avons réussi
		} catch (ControllerException exception) {
			setErrorMessage(exception.toString()); // Afficher le message d'erreur dans la fenêtre contextuelle
		}
	}

	@Override
	// Appelé lorsque le bouton de suppression est pressé.
	public void destructivePressed(ActionEvent event) {
		delete(); // Supprimer les données de la fenêtre contextuelle.
		data = null; // Réinitialiser les données de la fenêtre contextuelle.
		close(); // Fermer la fenêtre contextuelle.
	}
}
