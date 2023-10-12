package com.springboot.application.orthoapp.exceptions;

public class DataAlreadyExistsException extends RuntimeException {

	public DataAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public DataAlreadyExistsException(String message) {
		super(message);
		
	}

	public DataAlreadyExistsException(Throwable cause) {
		super(cause);
		
	}
	
	

}
