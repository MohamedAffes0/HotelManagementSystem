package org.models;

/**
 * Class used for passing data between the model and the UI. Content will be
 * displayed in the list button and style class will be assigned as the class
 * for that particular field in the button.
 */
public class ModelField {
	private String content = "";
	private String styleClass = "";

	public ModelField(String content, String styleClass) {
		this.content = content;
		this.styleClass = styleClass;
	}

	public String getContent() {
		return content;
	}

	public String getStyleClass() {
		return styleClass;
	}
}
