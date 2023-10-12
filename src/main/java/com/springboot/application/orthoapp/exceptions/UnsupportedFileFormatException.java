package com.springboot.application.orthoapp.exceptions;

public class UnsupportedFileFormatException extends RuntimeException {

	public UnsupportedFileFormatException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UnsupportedFileFormatException(String message) {
		super(message);
		
	}

	public UnsupportedFileFormatException(Throwable cause) {
		super(cause);
		
	}
	
	

}
