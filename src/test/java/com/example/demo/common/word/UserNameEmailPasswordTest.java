package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserNameEmailPasswordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		UserNameEmailPassword test = new UserNameEmailPassword(
				new NameWord("test"), 
				new EmailWord("test@example.com"),
				new PasswordWord("password"));
		
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "password");
	}
	
	/**
	 * 初期化テスト(文字列)
	 */
	@Test
	void initTest2() {
		UserNameEmailPassword test = new UserNameEmailPassword(
				"test", 
				"test@example.com", 
				"password");
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "password");
	}
	
	/**
	 * 初期化テスト(ブランク)
	 */
	@Test
	void initTest3() {
		UserNameEmailPassword test = new UserNameEmailPassword(
				"",
				"",
				"");
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getName(), "");
	}

	/**
	 * 初期化テスト(super)
	 */
	@Test
	void superInitTest() {
		SuperUserInterface test = new UserNameEmailPassword(
				new NameWord("test"), 
				new EmailWord("test@example.com"),
				new PasswordWord("password"));
		
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "password");
	}
	
	/**
	 * 初期化テスト(文字列)(super)
	 */
	@Test
	void superInitTest2() {
		SuperUserInterface test = new UserNameEmailPassword(
				"test", 
				"test@example.com", 
				"password");
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "password");
	}
	
	/**
	 * 初期化テスト(ブランク)(super)
	 */
	@Test
	void superInitTest3() {
		SuperUserInterface test = new UserNameEmailPassword(
				"",
				"",
				"");
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getName(), "");
	}
}
