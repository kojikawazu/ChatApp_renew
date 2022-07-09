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
 * 入室モデルクラスのテスト
 * @author nanai
 *
 */
class EnterModelTest {

	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTestNull() {
		EnterModel test = new EnterModel(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト(各クラス割り当て)
	 */
	@Test
	void initTest1() {
		LocalDateTime now = LocalDateTime.now();
		EnterModel test = new EnterModel(
						new EnterIdStatus(0), 
						new RoomIdStatus(0), 
						new UserIdStatus(0), 
						new UserIdStatus(0),
						new RoomMaxNumber(0),
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 0);
		Assertions.assertNotNull(test.getCreated());
	}

	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTest1_null() {
		LocalDateTime now = LocalDateTime.now();
		EnterModel test = new EnterModel(
				null, 
				null, 
				null, 
				null,
				null,
				now);

		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト(同一クラス割り当て)
	 */
	@Test
	void initTest1_sameClass() {
		LocalDateTime now = LocalDateTime.now();
		EnterModel dummy = new EnterModel(
						new EnterIdStatus(1), 
						new RoomIdStatus(2), 
						new UserIdStatus(3), 
						new UserIdStatus(4),
						new RoomMaxNumber(5),
						now);
		
		
		EnterModel test = new EnterModel(dummy);
		
		Assertions.assertEquals(test.getId(), 1);
		Assertions.assertEquals(test.getRoom_id(), 2);
		Assertions.assertEquals(test.getUser_id(), 3);
		Assertions.assertEquals(test.getManager_id(), 4);
		Assertions.assertEquals(test.getMax_sum(), 5);
		Assertions.assertNotNull(test.getCreated());
	}
	
	/**
	 * コンストラクタテスト(各クラス割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		EnterModel test = new EnterModel(
						new RoomIdStatus(0), 
						new UserIdStatus(0), 
						new UserIdStatus(0),
						new RoomMaxNumber(0),
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getManager_id(), 0);
		Assertions.assertEquals(test.getMax_sum(), 0);
		Assertions.assertNotNull(test.getCreated());
	}
}
