package org.models;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * Model: base class for all models files.
 * Contains a getContent abstract method that returns
 */
public abstract class Model {
	// Returns true if this object fits the filter criterea.
	public abstract boolean filter(TextField search, String filterType);

	public abstract ArrayList<ModelField> getFields();

	public SVGPath createIcon(String svgPath, double scale, String colorHex) {
		SVGPath icon = new SVGPath();
		icon.setContent(svgPath);
		icon.setScaleX(scale);
		icon.setScaleY(scale);
		icon.setFill(Color.web(colorHex));
		return icon;
	}
}
