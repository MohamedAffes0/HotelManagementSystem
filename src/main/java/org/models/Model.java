package org.models;

import java.util.ArrayList;

import javafx.scene.control.TextField;

/**
 * Model: base class for all models files.
 * Contains a getContent abstract method that returns
 */
public abstract class Model {
	// Returns true if this object fits the filter criterea.
	public abstract boolean filter(TextField search, String filterType);

	public ArrayList<Model> getData() {return null;}
}
