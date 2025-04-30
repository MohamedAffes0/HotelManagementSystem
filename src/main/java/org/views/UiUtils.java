package org.views;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class UiUtils {

	// Méthode pour définir une icône sur un bouton avec un chemin SVG spécifique, une échelle et une couleur
    public static void setIconToButton(Button button, String svgPath, double scale, String colorHex) {
		SVGPath icon = new SVGPath();
		icon.setContent(svgPath);
		icon.setScaleX(scale);
		icon.setScaleY(scale);
		icon.setFill(Color.web(colorHex));
		icon.setTranslateX(-5);
		button.setGraphic(icon);
		button.setContentDisplay(ContentDisplay.LEFT);
	}

	// Méthode pour créer une icône SVG avec un chemin spécifique, une échelle et une couleur
	public static SVGPath createIcon(String svgPath, double scale, String colorHex) {
		SVGPath icon = new SVGPath();
		icon.setContent(svgPath);
		icon.setScaleX(scale);
		icon.setScaleY(scale);
		icon.setFill(Color.web(colorHex));
		return icon;
	}
}
