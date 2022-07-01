package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * パスワードクラステスト
 * @author nanai
 *
 */
class PasswordWordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		PasswordWord test = new PasswordWord("password");
		Assertions.assertEquals(test.getString(), "password");
	}
	
	/**
	 * ブランクテスト_ブランクパターン
	 */
	@Test
	void isBlankTest_false() {
		PasswordWord test = new PasswordWord("password");
		Assertions.assertEquals(test.isBlank(), false);
	}
	
	/**
	 * ブランクテスト_ブランクなしパターン
	 */
	@Test
	void isBlankTest_true() {
		PasswordWord test = new PasswordWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}
	
	/**
	 * ブランクテスト(super)
	 */
	@Test
	void superInitTest() {
		SuperWord test = new PasswordWord("password");
		Assertions.assertEquals(test.getString(), "password");
	}
	
	/**
	 * ブランクテスト_ブランクパターン(super)
	 */
	@Test
	void superIsBlankTest_false() {
		SuperWord test = new PasswordWord("password");
		Assertions.assertEquals(test.isBlank(), false);
	}
	
	/**
	 * ブランクテスト_ブランクなしパターン(super)
	 */
	@Test
	void superIsBlankTest_true() {
		SuperWord test = new PasswordWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}

}
