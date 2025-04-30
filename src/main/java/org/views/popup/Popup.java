package org.views.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.views.popupfield.PopupField;

/**
 * AddPupup
 */
public abstract class Popup implements Initializable {

	public static boolean isPopupOpen = false;
	private ArrayList<PopupField> fields = new ArrayList<>(); // liste de tous les champs de la popup
	private String titleText; // le titre de la popup
	private String suggestedText = "Suggested"; // le texte du bouton ajouter ou modifier
	private String destuctiveText = "Destructive"; // le texte du bouton suprimer
	private String cancelText = "Cancel"; // le texte du bouton annuler
	final String filePath = "/popup.fxml"; // le chemin du fichier fxml de la popup

	@FXML
	private Label title;

	@FXML
	// Conteneur pour tous les champs de la popup.
	private VBox container;

	@FXML
	// Bouton qui effectue l'action prévue de la popup. Comme un ajout ou une mise à jour.
	private Button suggestedButton;

	@FXML
	// Un bouton rouge qui effectue une action destructrice comme supprimer.
	private Button destructiveButton;

	@FXML
	// Ferme la popup sans effectuer d'action.
	private Button cancelButton;

	@FXML
	private Label errorLabel;

	public Popup() {
	}

	public Popup(ArrayList<PopupField> fields) {
		this.fields = fields;
	}

	@FXML
	// Appelé lorsque le bouton annuler est pressé.
	public void cancelPressed(ActionEvent event) {
		Stage stage = (Stage) title.getScene().getWindow();
		stage.close();
	}

	@FXML
	// Appelé lorsque le bouton suggéré est pressé.
	public void suggestedPressed(ActionEvent event) {
	}

	@FXML
	// Appelé lorsque le bouton destructif est pressé.
	public void destructivePressed(ActionEvent event) {
	}

	@Override
	// Appelé lorsque le fichier fxml est entièrement chargé.
	public void initialize(URL location, ResourceBundle resources) {
		for (PopupField field : fields) {
			container.getChildren().add(field.getContainer());
		}
		title.setText(titleText);
		suggestedButton.setText(suggestedText);
		destructiveButton.setText(destuctiveText);
		cancelButton.setText(cancelText);
	}

	// Modifie le titre en haut de la popup.
	public void setTitle(String newTitle) {
		titleText = newTitle;
		if (title != null) {
			title.setText(newTitle);
		}
	}

	// Modifie le texte du bouton suggéré.
	public void setSuggestedText(String text) {
		suggestedText = text;
		if (suggestedButton != null)
			suggestedButton.setText(text);
	}

	// Modifie le texte du bouton destructif.
	public void setDestructiveText(String text) {
		destuctiveText = text;
		if (destructiveButton != null)
			destructiveButton.setText(text);
	}

	// Modifie le texte du bouton annuler.
	public void setCancelText(String text) {
		cancelText = text;
		if (cancelButton != null)
			cancelButton.setText(text);
	}

	// Modifie le texte de l'erreur.
	public void setErrorMessage(String message) {
		errorLabel.setText(message);
	}

	// si la popup est ouverte ou non.
	public static void setPopupOpen(boolean isPopupOpen) {
		Popup.isPopupOpen = isPopupOpen;
	}

	// Supprime le bouton destructif de la popup.
	public void removeDestructive() throws Exception {
		if (destructiveButton == null) {
			throw new Exception("The destructive button is either uninitialized or removed");
		}
		VBox buttonContainer = (VBox) destructiveButton.getParent();
		buttonContainer.getChildren().remove(buttonContainer.getChildren().indexOf(destructiveButton));
	}

	// Modifie les champs dans la popup.
	public void setFields(PopupField... fields) {
		this.fields = new ArrayList<PopupField>(Arrays.asList(fields));
	}

	// Renvoie le champ à l'index spécifié.
	public PopupField getField(int index) {
		return fields.get(index);
	}

	// Charge le fichier fxml et renvoie sa racine.
	public void load(String title, Runnable onClosed) throws IOException {
		if (isPopupOpen) {
			return;
		}

		setPopupOpen(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		// Définir soi-même comme contrôleur
		loader.setController(this);

		// Créez la fenêtre (stage) et configurez-la
		Stage stage = new Stage();
		Scene scene = new Scene(loader.load());
		stage.setResizable(false);
		stage.setTitle(title);
		stage.setScene(scene);
		// Recharger la liste après la fermeture de la popup.
		stage.setOnHidden(event -> windowClosed(onClosed));
		stage.show();
		isPopupOpen = true;
	}

	// Renvoie la fenêtre actuelle de la popup si elle est chargée, sinon renvoie null.
	public Window getWindow() {
		if (title == null) {
			return null;
		}

		return title.getScene().getWindow();
	}
	
	// Appelé lorsque la popup est fermée
	private void windowClosed(Runnable func) {
		func.run();
		isPopupOpen = false;
	}

	// ferme la popup.
	public void close() {
		Stage stage = (Stage) title.getScene().getWindow(); // Récupérer la fenêtre actuelle
		stage.close();
	}
}
