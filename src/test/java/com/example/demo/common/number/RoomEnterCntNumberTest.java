package com.example.demo.common.number;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 入室人数テスト
 * @author nanai
 *
 */
class RoomEnterCntNumberTest {

	/**
	 * 初期化テスト
	 */
	@Test
	void initTest() {
		RoomEnterCntNumber test = new RoomEnterCntNumber(0);
		Assertions.assertEquals(test.getNumber(), 0);
	}

	/**
	 * 初期化テスト(super)
	 */
	@Test
	void initTest2() {
		SuperNumber test = new RoomEnterCntNumber(0);
		Assertions.assertEquals(test.getNumber(), 0);
	}

}
