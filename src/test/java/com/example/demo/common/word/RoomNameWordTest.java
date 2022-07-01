package com.example.demo.common.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ルーム名クラステスト
 * @author nanai
 *
 */
class RoomNameWordTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		RoomNameWord test = new RoomNameWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン
	 */
	@Test
	void isBlankTest_false() {
		RoomNameWord test = new RoomNameWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン
	 */
	@Test
	void isBlankTest_true() {
		RoomNameWord test = new RoomNameWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}

	/**
	 * ブランクテスト(super)
	 */
	@Test
	void superInitTest() {
		SuperWord test = new RoomNameWord("test");
		Assertions.assertEquals(test.getString(), "test");
	}

	/**
	 * ブランクテスト_ブランクパターン(super)
	 */
	@Test
	void superIsBlankTest_false() {
		SuperWord test = new RoomNameWord("test");
		Assertions.assertEquals(test.isBlank(), false);
	}

	/**
	 * ブランクテスト_ブランクなしパターン(super)
	 */
	@Test
	void superIsBlankTest_true() {
		SuperWord test = new RoomNameWord("");
		Assertions.assertEquals(test.isBlank(), true);
	}
}
