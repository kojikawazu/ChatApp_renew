package com.example.demo.app.exception;

/**
 * データベース更新exception
 * @author nanai
 *
 */
public class DatabaseUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseUpdateException(String message) {
		super(message);
	}
}
