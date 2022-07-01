package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.status.LoginIdStatus;

/**
 * emailクラステスト
 * @author nanai
 *
 */
class EmailWordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		EmailWord test = new EmailWord("test@example.com");
		Assertions.assertEquals(test.getString(), "test@example.com");
	}
	
	/**
	 * ブランクテスト_ブランクパターン
	 */
	@Test
	void isBlankTest_false() {
		EmailWord test = new EmailWord("test@example.com");
		Assertions.assertEquals(test.isBlank(), false);
	}
	
	/**
	 * ブランクテスト_ブランクなしパターン
	 */
	@Test
	void isBlankTest_true() {
		EmailWord test = new EmailWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}
	
	/**
	 * ブランクテスト(super)
	 */
	@Test
	void superInitTest() {
		SuperWord test = new EmailWord("test@example.com");
		Assertions.assertEquals(test.getString(), "test@example.com");
	}
	
	/**
	 * ブランクテスト_ブランクパターン(super)
	 */
	@Test
	void superIsBlankTest_false() {
		SuperWord test = new EmailWord("test@example.com");
		Assertions.assertEquals(test.isBlank(), false);
	}
	
	/**
	 * ブランクテスト_ブランクなしパターン(super)
	 */
	@Test
	void superIsBlankTest_true() {
		SuperWord test = new EmailWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}

}
