package org.controllers.exceptions;

// exception levée lorsque une erreur se produit dans le controller
/**
 * ControllerException
 */
public class ControllerException extends Exception{

	public ControllerException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return getMessage();
	}

}
