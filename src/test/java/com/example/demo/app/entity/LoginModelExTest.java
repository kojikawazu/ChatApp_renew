package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.NameWord;

/**
 * ログインモデル拡張版クラスのテスト
 * @author nanai
 *
 */
class LoginModelExTest {

	/**
	 * コンストラクタテスト(LoginModel割り当て)
	 */
	@Test
	void initTest() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				new LoginIdStatus(0), 
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		LoginModelEx test = new LoginModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "");
	}
	
	/**
	 * コンストラクタテスト(LoginModel割り当て - null)
	 */
	@Test
	void initTest_null() {
		LoginModelEx test = new LoginModelEx(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertEquals(test.getUserName(), "");
	}
	
	/**
	 * コンストラクタテスト(LoginModelとNameWordクラス割り当て)
	 */
	@Test
	void initTest_loginModel_string() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				new LoginIdStatus(0), 
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		LoginModelEx test = new LoginModelEx(
				dummy, 
				new NameWord("test"));
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "test");
	}
	
	/**
	 * コンストラクタテスト(LoginModel割り当て)(super)
	 */
	@Test
	void initTest_superClass() {
		LocalDateTime now = LocalDateTime.now();
		
		LoginModel dummy = new LoginModel(
				new LoginIdStatus(0), 
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		LoginModel test = new LoginModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
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
		
		LoginModel dummySuper = new LoginModel(
				new LoginIdStatus(0), 
				new RoomIdStatus(0), 
				new UserIdStatus(0), 
				now);
		
		LoginModelEx dummy = new LoginModelEx(
				dummySuper, 
				new NameWord("test"));
		
		LoginModelEx test = new LoginModelEx(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getRoom_id(), 0);
		Assertions.assertEquals(test.getUser_id(), 0);
		Assertions.assertEquals(test.getCreated(), now);
		Assertions.assertEquals(test.getUserName(), "test");
	}
}
