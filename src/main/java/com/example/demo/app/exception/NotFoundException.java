package com.example.demo.app.exception;

/**
 * NotFoundException
 * @author nanai
 *
 */
public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String message) {
		super(message);
	}
	
}
