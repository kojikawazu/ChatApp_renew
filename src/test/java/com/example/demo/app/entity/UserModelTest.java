package com.example.demo.app.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.PasswordWord;

/**
 * ユーザモデルクラスのテスト
 * @author nanai
 *
 */
class UserModelTest {

	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTest_UserModel_Null() {
		UserModel test = new UserModel(null);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getEmail(), "");
		Assertions.assertEquals(test.getPasswd(), "");
		Assertions.assertEquals(test.getForgot_passwd(), "");
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(各クラス割り当て)
	 */
	@Test
	void initTest1() {
		LocalDateTime now = LocalDateTime.now();
		UserModel test = new UserModel(
						new UserIdStatus(0),
						new NameWord("name"),
						new EmailWord("test@example.com"), 
						new PasswordWord("password"), 
						new PasswordWord("forgot_passwd"), 
						now, 
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "name");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPasswd(), "password");
		Assertions.assertEquals(test.getForgot_passwd(), "forgot_passwd");
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(null割り当て)
	 */
	@Test
	void initTest1_null() {
		LocalDateTime now = LocalDateTime.now();
		UserModel test = new UserModel(
						null, 
						null, 
						null,
						null,
						null,
						now, 
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "");
		Assertions.assertEquals(test.getEmail(), "");
		Assertions.assertEquals(test.getPasswd(), "");
		Assertions.assertEquals(test.getForgot_passwd(), "");
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(同一クラス割り当て)
	 */
	@Test
	void initTest1_sameClass() {
		LocalDateTime now = LocalDateTime.now();
		
		UserModel dummy = new UserModel(
				new UserIdStatus(0),
				new NameWord("name"),
				new EmailWord("test@example.com"), 
				new PasswordWord("password"), 
				new PasswordWord("forgot_passwd"), 
				now, 
				now);
		
		UserModel test = new UserModel(dummy);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "name");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPasswd(), "password");
		Assertions.assertEquals(test.getForgot_passwd(), "forgot_passwd");
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * コンストラクタテスト(6パラメータ割り当て)
	 */
	@Test
	void initTest2() {
		LocalDateTime now = LocalDateTime.now();
		UserModel test = new UserModel(
						new NameWord("name"),
						new EmailWord("test@example.com"), 
						new PasswordWord("password"), 
						new PasswordWord("forgot_passwd"), 
						now, 
						now);
		
		Assertions.assertEquals(test.getId(), 0);
		Assertions.assertEquals(test.getName(), "name");
		Assertions.assertEquals(test.getEmail(), "test@example.com");
		Assertions.assertEquals(test.getPasswd(), "password");
		Assertions.assertEquals(test.getForgot_passwd(), "forgot_passwd");
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
}
