package com.cognizant.hackfse.eauction.exception;

public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = -2706816991569806145L;

	public NotFoundException(String message) {
		super(message);
	}
}
