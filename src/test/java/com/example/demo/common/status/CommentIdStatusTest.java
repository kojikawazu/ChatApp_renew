package com.example.demo.common.status;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * コメントIDテスト
 * @author nanai
 *
 */
class CommentIdStatusTest {

	/**
	 * コメントID取得テスト
	 */
	@Test
	void initTest() {
		CommentIdStatus test = new CommentIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}

	/**
	 * エラー判定のテスト(true)
	 */
	@Test
	void errorNumbertest() {
		CommentIdStatus test = new CommentIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}

	/**
	 * エラー判定のテスト(false)
	 */
	@Test
	void noErrorNumbertest() {
		CommentIdStatus test = new CommentIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}

	/**
	 * ID取得テスト(superクラス)
	 */
	@Test
	void superInitTest() {
		SuperStatus test = new CommentIdStatus(0);
		Assertions.assertEquals(test.getId(), 0);
	}

	/**
	 * エラー判定のテスト(true)(superクラス)
	 */
	@Test
	void superErrorNumbertest() {
		SuperStatus test = new CommentIdStatus(-1);
		Assertions.assertEquals(test.isError(), true);
	}

	/**
	 * エラー判定のテスト(false)(superクラス)
	 */
	@Test
	void superNoErrorNumbertest() {
		SuperStatus test = new CommentIdStatus(0);
		Assertions.assertEquals(test.isError(), false);
	}

}
