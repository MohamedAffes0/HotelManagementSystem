package org.controllers.exceptions;

/**
 * ConnectionUnavailableException
 */
public class ConnectionUnavailableException extends DBException {

	public ConnectionUnavailableException() {
		super("La connextion n'est pas disponible.");
	}

}
