package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserNameEmailTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		UserNameEmail test = new UserNameEmail(
				new NameWord("test"), 
				new EmailWord("test@example.com"));
		
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "");
	}
	
	/**
	 * 初期化テスト(文字列)
	 */
	@Test
	void initTest2() {
		UserNameEmail test = new UserNameEmail(
				"test", 
				"test@example.com");
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "");
	}
	
	/**
	 * 初期化テスト(ブランク)
	 */
	@Test
	void initTest3() {
		UserNameEmail test = new UserNameEmail(
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
		SuperUserInterface test = new UserNameEmail(
				new NameWord("test"), 
				new EmailWord("test@example.com"));
		
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "");
	}
	
	/**
	 * 初期化テスト(文字列)(super)
	 */
	@Test
	void superInitTest2() {
		SuperUserInterface test = new UserNameEmail(
				"test", 
				"test@example.com");
		Assertions.assertEquals(test.getName(), "test");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPassword(), "");
	}
	
	/**
	 * 初期化テスト(ブランク)(super)
	 */
	@Test
	void superInitTest3() {
		SuperUserInterface test = new UserNameEmail(
				"",
				"");
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getName(), "");
	}

}
