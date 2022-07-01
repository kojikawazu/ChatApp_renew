package com.example.demo.common.number;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 最大人数クラステスト
 * @author nanai
 *
 */
class RoomMaxNumberTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		RoomMaxNumber test = new RoomMaxNumber(0);
		Assertions.assertEquals(test.getNumber(), 0);
	}

	/**
	 * 初期化テスト(super)
	 */
	@Test
	void initTest2() {
		SuperNumber test = new RoomMaxNumber(0);
		Assertions.assertEquals(test.getNumber(), 0);
	}
}
