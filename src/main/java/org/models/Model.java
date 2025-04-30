package org.models;

import java.util.ArrayList;


/**
 * Model: base class for all models files.
 * Contains a getContent abstract method that returns
 */
public abstract class Model {

	public abstract ArrayList<ModelField> getFields();

	public abstract int getId();

}
