package org.controllers.exceptions;

/**
 * DBException
 */
public class DBException extends ControllerException{
	
	public DBException() {
		super("Erreur de connection a la base de données");
	}

	public DBException(String message) {
		super(message);
	}
}
