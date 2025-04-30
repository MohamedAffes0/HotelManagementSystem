package org.controllers.exceptions;

// exception levée lorsque la connexion à la base de données n'est pas disponible
/**
 * ConnectionUnavailableException
 */
public class ConnectionUnavailableException extends DBException {

	public ConnectionUnavailableException() {
		super("La connextion n'est pas disponible.");
	}

}
