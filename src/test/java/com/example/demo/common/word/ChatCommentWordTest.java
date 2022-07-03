package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * チャットコメントクラステスト
 * @author nanai
 *
 */
class ChatCommentWordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		ChatCommentWord test = new ChatCommentWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン
	 */
	@Test
	void isBlankTest_false() {
		ChatCommentWord test = new ChatCommentWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン
	 */
	@Test
	void isBlankTest_true() {
		ChatCommentWord test = new ChatCommentWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}

	/**
	 * ブランクテスト(super)
	 */
	@Test
	void superInitTest() {
		SuperWord test = new ChatCommentWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン(super)
	 */
	@Test
	void superIsBlankTest_false() {
		SuperWord test = new ChatCommentWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン(super)
	 */
	@Test
	void superIsBlankTest_true() {
		SuperWord test = new ChatCommentWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}
}
