package org.app.user;

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
