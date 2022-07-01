package com.example.demo.common.status;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.SuperStatus;

/**
 * ルームIDテスト
 * @author nanai
 *
 */
class RoomIdStatusTest {

	/**
	 * ユーザーID取得テスト
	 */
	@Test
	void initTest() {
		RoomIdStatus test = new RoomIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}
	
	/**
	 * エラー判定のテスト(true)
	 */
	@Test
	void errorNumbertest() {
		RoomIdStatus test = new RoomIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}
	
	/**
	 * エラー判定のテスト(false)
	 */
	@Test
	void noErrorNumbertest() {
		RoomIdStatus test = new RoomIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}
	
	/**
	 * ユーザーID取得テスト(superクラス)
	 */
	@Test
	void superInitTest() {
		SuperStatus test = new RoomIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}
	
	/**
	 * エラー判定のテスト(true)(superクラス)
	 */
	@Test
	void superErrorNumbertest() {
		SuperStatus test = new RoomIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}
	
	/**
	 * エラー判定のテスト(false)(superクラス)
	 */
	@Test
	void superNoErrorNumbertest() {
		SuperStatus test = new RoomIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}

}
