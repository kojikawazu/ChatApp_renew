package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.number.RoomEnterCntNumber;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.RoomCommentWord;
import com.example.demo.common.word.RoomNameWord;
import com.example.demo.common.word.RoomTagWord;

/**
 * ルームモデル拡張版クラスのテスト
 * @author nanai
 *
 */
class RoomModelExTest {

	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTestNull() {
		RoomModelEx test = new RoomModelEx(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getComment(), "");
		Assertions.assertEquals(test.getTag(), "");
		Assertions.assertEquals(test.getMax_roomsum(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
		Assertions.assertEquals(test.getUserName(), "");
		Assertions.assertEquals(test.getEnterCnt(), 0);
	}

	/**
	 * コンストラクタテスト(各クラス割り当て)
	 */
	@Test
	void initTest1() {
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
		
		RoomModelEx test = new RoomModelEx(
				dummy,
				new NameWord("テスト"),
				new RoomEnterCntNumber(0));
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "room_test");
		Assertions.assertEquals(test.getComment(), "room_comment");
		Assertions.assertEquals(test.getTag(), "room_tag");
		Assertions.assertEquals(test.getMax_roomsum(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
		Assertions.assertEquals(test.getUserName(), "");
		Assertions.assertEquals(test.getEnterCnt(), 0);
	}
	
	/**
	 * コンストラクタテスト(RoomModelExクラス割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		RoomModel superDummy = new RoomModel(
						new RoomIdStatus(0),
						new RoomNameWord("room_test"),
						new RoomCommentWord("room_comment"),
						new RoomTagWord("room_tag"),
						new RoomMaxNumber(0),
						new UserIdStatus(0),
						now, 
						now);
		
		RoomModelEx dummy = new RoomModelEx(
				superDummy,
				new NameWord("テスト"),
				new RoomEnterCntNumber(0));
		
		RoomModelEx test = new RoomModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "room_test");
		Assertions.assertEquals(test.getComment(), "room_comment");
		Assertions.assertEquals(test.getTag(), "room_tag");
		Assertions.assertEquals(test.getMax_roomsum(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
		Assertions.assertEquals(test.getUserName(), "");
		Assertions.assertEquals(test.getEnterCnt(), 0);
	}
}
