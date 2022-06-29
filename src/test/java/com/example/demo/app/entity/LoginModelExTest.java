package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoginModelExTest {

	/**
	 * コンストラクタテスト(LoginModel割り当て)
	 */
	@Test
	void initTest() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				0,
				0,
				0,
				now);
		LoginModelEx test = new LoginModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "");
	}
	
	/**
	 * コンストラクタテスト(LoginModel割り当て)
	 */
	@Test
	void initTest_null() {
		LoginModelEx test = new LoginModelEx(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertEquals(test.getUserName(), "");
	}
	
	/**
	 * コンストラクタテスト(LoginModelと文字列割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				0,
				0,
				0,
				now);
		LoginModelEx test = new LoginModelEx(dummy, "test");
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "test");
	}
	
	/**
	 * コンストラクタテスト(LoginModel)
	 */
	@Test
	void initTest_superClass() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				0,
				0,
				0,
				now);
		LoginModel test = new LoginModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト(LoginModel)
	 */
	@Test
	void initTest_myClass() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummySuper = new LoginModel(
				0,
				0,
				0,
				now);
		LoginModelEx dummy = new LoginModelEx(dummySuper, "test");
		LoginModelEx test = new LoginModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "test");
	}

}
