package com.springboot.application.orthoapp.exceptions;

public class DataNotFoundException extends RuntimeException {

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public DataNotFoundException(String message) {
		super(message);
		
	}

	public DataNotFoundException(Throwable cause) {
		super(cause);
		
	}
	
	

}
