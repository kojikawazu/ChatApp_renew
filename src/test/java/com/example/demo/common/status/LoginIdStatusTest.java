package com.example.demo.common.status;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ログインIDテスト
 * @author nanai
 *
 */
class LoginIdStatusTest {

	/**
	 * ユーザーID取得テスト
	 */
	@Test
	void initTest() {
		LoginIdStatus test = new LoginIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}

	/**
	 * エラー判定のテスト(true)
	 */
	@Test
	void errorNumbertest() {
		LoginIdStatus test = new LoginIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}

	/**
	 * エラー判定のテスト(false)
	 */
	@Test
	void noErrorNumbertest() {
		LoginIdStatus test = new LoginIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}

	/**
	 * ユーザーID取得テスト(superクラス)
	 */
	@Test
	void superInitTest() {
		SuperStatus test = new LoginIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}

	/**
	 * エラー判定のテスト(true)(superクラス)
	 */
	@Test
	void superErrorNumbertest() {
		SuperStatus test = new LoginIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}

	/**
	 * エラー判定のテスト(false)(superクラス)
	 */
	@Test
	void superNoErrorNumbertest() {
		SuperStatus test = new LoginIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}
}
