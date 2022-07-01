package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.RoomCommentWord;
import com.example.demo.common.word.RoomNameWord;
import com.example.demo.common.word.RoomTagWord;

/**
 * ルームモデルのテスト
 * @author nanai
 *
 */
class RoomModelTest {

	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTestNull() {
		RoomModel test = new RoomModel(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getTag(), "");
		Assertions.assertEquals(test.getMax_roomsum(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(各クラス割り当て)
	 */
	@Test
	void initTest1() {
		LocalDateTime now = LocalDateTime.now();
		RoomModel test = new RoomModel(
						new RoomIdStatus(0),
						new RoomNameWord("room_test"),
						new RoomCommentWord("room_comment"),
						new RoomTagWord("room_tag"),
						new RoomMaxNumber(0),
						new UserIdStatus(0),
						now, 
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "room_test");
		Assertions.assertEquals(test.getComment(), "room_comment");
		Assertions.assertEquals(test.getTag(), "room_tag");
		Assertions.assertEquals(test.getMax_roomsum(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTest3_null() {
		LocalDateTime now = LocalDateTime.now();
		RoomModel test = new RoomModel(
				null,
				null,
				null,
				null,
				null,
				null,
				now, 
				now);

		Assertions.assertEquals(test.getName(), "room_test");
		Assertions.assertEquals(test.getComment(), "room_comment");
		Assertions.assertEquals(test.getTag(), "room_tag");
		Assertions.assertEquals(test.getMax_roomsum(), 0);
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(同一クラス割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		RoomModel dummy = new RoomModel(
						new RoomIdStatus(0),
						new RoomNameWord("room_test"),
						new RoomCommentWord("room_comment"),
						new RoomTagWord("room_tag"),
						new RoomMaxNumber(0),
						new UserIdStatus(0),
						now, 
						now);
		
		RoomModel test = new RoomModel(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "room_test");
		Assertions.assertEquals(test.getComment(), "room_comment");
		Assertions.assertEquals(test.getTag(), "room_tag");
		Assertions.assertEquals(test.getMax_roomsum(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}

}
