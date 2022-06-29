package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.app.config.WebFunctions;

class LoginModelTest {

	/**
	 * コンストラクタテスト(各値割り当て)
	 */
	@Test
	void initTest() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel test = new LoginModel(
				0,
				0,
				0,
				now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
	}
	
	/**
	 * コンストラクタテスト(同一クラス割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				0,
				0,
				0,
				now);
		LoginModel test = new LoginModel(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
	}
	
	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTestNull() {
		LocalDateTime now = LocalDateTime.now();
		LoginModel test = new LoginModel(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
	}

}
