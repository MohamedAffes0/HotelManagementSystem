package org.models;

import javafx.scene.control.ContentDisplay;
import javafx.scene.shape.SVGPath;

/**
 * Class used for passing data between the model and the UI. Content will be
 * displayed in the list button and style class will be assigned as the class
 * for that particular field in the button.
 */
public class ModelField {
	private String content = "";
	private String styleClass;
	private SVGPath icon;
	private ContentDisplay iconPosition;

	public ModelField(String content, String styleClass, SVGPath icon, ContentDisplay iconPosition) {
		this.content = content;
		this.styleClass = styleClass;
		this.icon = icon;
		this.iconPosition = iconPosition;
	}

	public ModelField(String content, String styleClass, SVGPath icon) {
		this.content = content;
		this.styleClass = styleClass;
		this.icon = icon;
		this.iconPosition = ContentDisplay.LEFT;
	}

	public ModelField(String content, String styleClass) {
		this.content = content;
		this.styleClass = styleClass;
		this.icon = null;
	}

	public ModelField(String content) {
		this.content = content;
		this.styleClass = null;
		this.icon = null;
	}

	public String getContent() {
		return content;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public SVGPath getIcon() {
		return icon;
	}

	public ContentDisplay getIconPosition() {
		return iconPosition;
	}
}
