package com.example.demo.app.status;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserIdStatusTest {

	/**
	 * ユーザーID取得テスト
	 */
	@Test
	void initTest() {
		UserIdStatus test = new UserIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}
	
	/**
	 * エラー判定のテスト(true)
	 */
	@Test
	void errorNumbertest() {
		UserIdStatus test = new UserIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}
	
	/**
	 * エラー判定のテスト(false)
	 */
	@Test
	void noErrorNumbertest() {
		UserIdStatus test = new UserIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}
	
	/**
	 * ユーザーID取得テスト(superクラス)
	 */
	@Test
	void superInitTest() {
		SuperStatus test = new UserIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}
	
	/**
	 * エラー判定のテスト(true)(superクラス)
	 */
	@Test
	void superErrorNumbertest() {
		SuperStatus test = new UserIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}
	
	/**
	 * エラー判定のテスト(false)(superクラス)
	 */
	@Test
	void superNoErrorNumbertest() {
		SuperStatus test = new UserIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}

}
