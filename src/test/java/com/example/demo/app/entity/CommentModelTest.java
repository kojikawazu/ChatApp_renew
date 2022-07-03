package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;

/**
 * コメントモデルクラスのテスト
 * @author nanai
 *
 */
class CommentModelTest {

	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTestNull() {
		CommentModel test = new CommentModel(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト(各クラス割り当て)
	 */
	@Test
	void initTest1() {
		LocalDateTime now = LocalDateTime.now();
		CommentModel test = new CommentModel(
						new CommentIdStatus(0),
						new ChatCommentWord(""),
						new RoomIdStatus(0), 
						new UserIdStatus(0), 
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
	}

	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTest3_null() {
		LocalDateTime now = LocalDateTime.now();
		CommentModel test = new CommentModel(
				null,
				null,
				null, 
				null, 
				now);

		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト(同一クラス割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		CommentModel dummy = new CommentModel(
				new CommentIdStatus(1),
				new ChatCommentWord("test"),
				new RoomIdStatus(2), 
				new UserIdStatus(3), 
				now);
		CommentModel test = new CommentModel(dummy);
		
		Assertions.assertEquals(test.getId(), 1);
		Assertions.assertEquals(test.getComment(), "test");
		Assertions.assertEquals(test.getRoom_id(), 2);
		Assertions.assertEquals(test.getUser_id(), 3);
		Assertions.assertNotNull(test.getCreated());
	}
}
