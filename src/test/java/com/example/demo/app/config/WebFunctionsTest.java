package com.example.demo.app.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.app.status.LoginIdStatus;
import com.example.demo.app.status.SuperStatus;

class WebFunctionsTest {

	/**
	 * isNotNullメソッドのnullパターンのテスト
	 */
	@Test
	void testMethod_isNotNull_null() {
		SuperStatus test = null;
		Assertions.assertEquals(WebFunctions.isNotNull(test), false);
	}
	
	/**
	 * isNotNullメソッドのnullじゃないパターンのテスト
	 */
	@Test
	void testMethod_isNotNull() {
		SuperStatus test = new LoginIdStatus(0);
		Assertions.assertEquals(WebFunctions.isNotNull(test), true);
	}

}
