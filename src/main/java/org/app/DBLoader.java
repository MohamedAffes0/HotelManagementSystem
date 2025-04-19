package org.app;

import java.util.ArrayList;

import org.models.Model;

/**
 * DBLoader
 */
public abstract class DBLoader {
	public abstract void load();
	public abstract ArrayList<? extends Model> getData();
}
