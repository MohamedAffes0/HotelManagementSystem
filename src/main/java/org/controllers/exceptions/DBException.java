package org.controllers.exceptions;

// exception levée lorsque la connexion à la base de données n'est pas disponible
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
