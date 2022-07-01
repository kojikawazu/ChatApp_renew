package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ルームコメントクラステスト
 * @author nanai
 *
 */
class RoomCommentWordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		RoomCommentWord test = new RoomCommentWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン
	 */
	@Test
	void isBlankTest_false() {
		RoomCommentWord test = new RoomCommentWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン
	 */
	@Test
	void isBlankTest_true() {
		RoomCommentWord test = new RoomCommentWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}

	/**
	 * ブランクテスト(super)
	 */
	@Test
	void superInitTest() {
		SuperWord test = new RoomCommentWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン(super)
	 */
	@Test
	void superIsBlankTest_false() {
		SuperWord test = new RoomCommentWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン(super)
	 */
	@Test
	void superIsBlankTest_true() {
		SuperWord test = new RoomCommentWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}
}
