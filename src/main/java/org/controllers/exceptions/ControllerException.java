package org.controllers.exceptions;

/**
 * ControllerException
 */
// exception levée lorsque une erreur se produit dans le controller
public class ControllerException extends Exception{

	public ControllerException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return getMessage();
	}

}
