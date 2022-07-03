package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;
import com.example.demo.common.word.NameWord;

/**
 * コメントモデル拡張版クラスのテスト
 * @author nanai
 *
 */
class CommentModelExTest {

	/**
	 * コンストラクタテスト(CommentModel割り当て)
	 */
	@Test
	void initTest() {
		LocalDateTime now = LocalDateTime.now();
		
		CommentModel dummy = new CommentModel(
				new CommentIdStatus(0),
				new ChatCommentWord(""),
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		CommentModelEx test = new CommentModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "");
	}
	
	/**
	 * コンストラクタテスト(CommentModel割り当て - null)
	 */
	@Test
	void initTest_null() {
		CommentModelEx test = new CommentModelEx(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertEquals(test.getUserName(), "");
	}
	
	/**
	 * コンストラクタテスト(CommentModelとNameWordクラス割り当て)
	 */
	@Test
	void initTest_loginModel_string() {
		LocalDateTime now = LocalDateTime.now();
		
		CommentModel dummy = new CommentModel(
				new CommentIdStatus(0),
				new ChatCommentWord(""),
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		CommentModelEx test = new CommentModelEx(
				dummy, 
				new NameWord("test"));
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertEquals(test.getUserName(), "test");
	}
	
	/**
	 * コンストラクタテスト(CommentModel割り当て)(super)
	 */
	@Test
	void initTest_superClass() {
		LocalDateTime now = LocalDateTime.now();
		
		CommentModel dummy = new CommentModel(
				new CommentIdStatus(0),
				new ChatCommentWord(""),
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		CommentModel test = new CommentModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト(LoginModelEx割り当て)
	 */
	@Test
	void initTest_myClass() {
		LocalDateTime now = LocalDateTime.now();
		
		CommentModel dummySuper = new CommentModel(
				new CommentIdStatus(0),
				new ChatCommentWord(""),
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		CommentModelEx dummy = new CommentModelEx(
				dummySuper, 
				new NameWord("test"));
		
		CommentModelEx test = new CommentModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "test");
	}
}
