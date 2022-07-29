package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ログインモデルクラスのテスト
 * @author nanai
 *
 */
class LoginModelTest {
	
	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTestNull() {
		LoginModel test = new LoginModel(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
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
		LoginModel test = new LoginModel(
						new LoginIdStatus(0), 
						new RoomIdStatus(0), 
						new UserIdStatus(0), 
						now, 
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTest1_null() {
		LocalDateTime now = LocalDateTime.now();
		LoginModel test = new LoginModel(
						null, 
						null, 
						null, 
						now,
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(同一クラス割り当て)
	 */
	@Test
	void initTest1_sameClass() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				new LoginIdStatus(0), 
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now,
				now);
		
		LoginModel test = new LoginModel(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUpdated(), now);
	}
	
	/**
	 * コンストラクタテスト(3パラメータ割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel test = new LoginModel(
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now,
				now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUpdated(), now);
	}
}
