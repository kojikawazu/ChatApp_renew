package com.example.demo.common.status;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 入室IDテスト
 * @author nanai
 *
 */
class EnterIdStatusTest {

	/**
	 * 入室ID取得テスト
	 */
	@Test
	void initTest() {
		EnterIdStatus test = new EnterIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}

	/**
	 * エラー判定のテスト(true)
	 */
	@Test
	void errorNumbertest() {
		EnterIdStatus test = new EnterIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}

	/**
	 * エラー判定のテスト(false)
	 */
	@Test
	void noErrorNumbertest() {
		EnterIdStatus test = new EnterIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}

	/**
	 * ID取得テスト(superクラス)
	 */
	@Test
	void superInitTest() {
		SuperStatus test = new EnterIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}

	/**
	 * エラー判定のテスト(true)(superクラス)
	 */
	@Test
	void superErrorNumbertest() {
		SuperStatus test = new EnterIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}

	/**
	 * エラー判定のテスト(false)(superクラス)
	 */
	@Test
	void superNoErrorNumbertest() {
		SuperStatus test = new EnterIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}
}
