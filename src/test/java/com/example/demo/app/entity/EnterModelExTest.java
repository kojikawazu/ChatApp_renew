package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 入室モデル拡張版テスト
 * @author nanai
 *
 */
class EnterModelExTest {

	/**
	 * コンストラクタテスト
	 * (入室モデル渡し)
	 */
	@Test
	void initTest() {
		LocalDateTime now = LocalDateTime.now();
		
		EnterModel superModel = new EnterModel(
				new EnterIdStatus(0),
				new RoomIdStatus(0),
				new UserIdStatus(0),
				new UserIdStatus(0),
				now);
		
		EnterModelEx test = new EnterModelEx(superModel);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト
	 * (null)
	 */
	@Test
	void initTest_null() {
		EnterModelEx test = new EnterModelEx(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト
	 * (入室モデル, 入室最大数渡し)
	 */
	@Test
	void initTest_superModel_maxsum() {
		LocalDateTime now = LocalDateTime.now();
		
		EnterModel superModel = new EnterModel(
				new EnterIdStatus(0),
				new RoomIdStatus(0),
				new UserIdStatus(0),
				new UserIdStatus(0),
				now);
		
		EnterModelEx test = new EnterModelEx(
				superModel,
				new RoomMaxNumber(1));
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 1);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト
	 * (入室モデル渡し)
	 */
	@Test
	void initTest_sameModel() {
		LocalDateTime now = LocalDateTime.now();
		
		EnterModel superModel = new EnterModel(
				new EnterIdStatus(0),
				new RoomIdStatus(0),
				new UserIdStatus(0),
				new UserIdStatus(0),
				now);
		
		EnterModelEx model = new EnterModelEx(
				superModel,
				new RoomMaxNumber(1));
		
		EnterModelEx test = new EnterModelEx(model);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 1);
		Assertions.assertNotNull(test.getCreated());
	}
}
