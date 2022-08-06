package com.example.demo.app.exception;

/**
 * Exceptionのthrow用
 * @author nanai
 *
 */
public class WebMvcConfig {
	
	public static String EXCEPTION_NOTFOUND = "Can't find the same ID";

	public static String DATABASE_NOT_UPDATE = "Database Update...";
	
	public static NotFoundException NOT_FOUND() {
		return new NotFoundException(WebMvcConfig.EXCEPTION_NOTFOUND);
	}
	
	public static DatabaseUpdateException NOT_DATA_UPDATE() {
		return new DatabaseUpdateException(DATABASE_NOT_UPDATE);
	}

}
