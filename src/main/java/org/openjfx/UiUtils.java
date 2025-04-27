package org.openjfx;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class UiUtils {
    public static void setIconToButton(Button button, String svgPath, double scale, String colorHex) {
		SVGPath icon = new SVGPath();
		icon.setContent(svgPath);
		icon.setScaleX(scale);
		icon.setScaleY(scale);
		icon.setFill(Color.web(colorHex));
		button.setGraphic(icon);
		button.setContentDisplay(ContentDisplay.LEFT);
	}
}
