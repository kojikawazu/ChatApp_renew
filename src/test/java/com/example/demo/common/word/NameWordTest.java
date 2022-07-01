package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 名前クラステスト
 * @author nanai
 *
 */
class NameWordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		NameWord test = new NameWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}
	
	/**
	 * ブランクテスト_ブランクパターン
	 */
	@Test
	void isBlankTest_false() {
		NameWord test = new NameWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}
	
	/**
	 * ブランクテスト_ブランクなしパターン
	 */
	@Test
	void isBlankTest_true() {
		NameWord test = new NameWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}
	
	/**
	 * ブランクテスト(super)
	 */
	@Test
	void superInitTest() {
		SuperWord test = new NameWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}
	
	/**
	 * ブランクテスト_ブランクパターン(super)
	 */
	@Test
	void superIsBlankTest_false() {
		SuperWord test = new NameWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}
	
	/**
	 * ブランクテスト_ブランクなしパターン(super)
	 */
	@Test
	void superIsBlankTest_true() {
		SuperWord test = new NameWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}

}
