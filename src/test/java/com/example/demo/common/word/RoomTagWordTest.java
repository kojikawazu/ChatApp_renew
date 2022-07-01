package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ルームタグクラステスト
 * @author nanai
 *
 */
class RoomTagWordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		RoomTagWord test = new RoomTagWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン
	 */
	@Test
	void isBlankTest_false() {
		RoomTagWord test = new RoomTagWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン
	 */
	@Test
	void isBlankTest_true() {
		RoomTagWord test = new RoomTagWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}

	/**
	 * ブランクテスト(super)
	 */
	@Test
	void superInitTest() {
		SuperWord test = new RoomTagWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン(super)
	 */
	@Test
	void superIsBlankTest_false() {
		SuperWord test = new RoomTagWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン(super)
	 */
	@Test
	void superIsBlankTest_true() {
		SuperWord test = new RoomTagWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}
}
