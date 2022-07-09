package com.example.demo.app.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.demo.app.entity.UserModel;
import com.example.demo.common.dao.UserDaoSql;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.PasswordWord;
import com.example.demo.common.word.UserNameEmail;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ユーザーへのDBアクセスクラスのテスト
 * @author nanai
 *
 */
@ExtendWith(MockitoExtension.class)
class UserDaoSqlTest {

	/**
	 * フィールド
	 * 
	 */
	@Mock
	private JdbcTemplate jdbcTemp;
	
	@InjectMocks
	private UserDaoSql userDaoSql;
	
	@Mock
	private UserDaoSql userDaoSql2;
	
	/** ユーザーモデル	*/
	private UserModel model;
	
	/** ユーザーフォーム	*/
	private UserNameEmailPassword user;
	
	/** ユーザーフォーム2	*/
	private UserNameEmail userEmail;
	
	/** 生成日時     */
	private LocalDateTime created = LocalDateTime.now();
	
	/** 更新日時     */
	private LocalDateTime updated = LocalDateTime.now();
	
	/**
	 * 下準備
	 */
	@BeforeEach
	void before() {
		NameWord name       = new NameWord("test");
		EmailWord email     = new EmailWord("test@example.com");
		PasswordWord passwd = new PasswordWord("password");
		PasswordWord forgot = new PasswordWord("forgot");
		
		this.model = new UserModel(
				new UserIdStatus(0),
				name,
				email,
				passwd,
				forgot,
				created,
				updated);
		
		this.user = new UserNameEmailPassword(
				name, 
				email, 
				passwd);
		
		this.userEmail = new UserNameEmail(
				name, 
				email);
	}
	
	/**
	 * 追加テスト
	 */
	@Test
	void insert_test(){
		doNothing().when(this.userDaoSql2).insert(model);
		this.userDaoSql2.insert(model);
		verify(userDaoSql2, times(1)).insert(model);
	}
	
	/**
	 * 追加テスト_jdbcTemplate呼び出す回数テスト
	 */
	@Test
	void insert_jdbcTemp_test(){
		this.userDaoSql.insert(model);
		verify(jdbcTemp, times(1)).update(
				ArgumentMatchers.anyString(), 
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.any(LocalDateTime.class),
				ArgumentMatchers.any(LocalDateTime.class));
	}
	
	/**
	 * 追加_jdbcTempleteの呼び出し回数テスト2
	 * 
	 */
	@Test
	void insert_jdbcTemp_null_test() {
		this.userDaoSql.insert(null);
		verify(jdbcTemp, times(0)).update(
				ArgumentMatchers.anyString(), 
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.any(LocalDateTime.class),
				ArgumentMatchers.any(LocalDateTime.class));
	}
	
	/**
	 * 追加テスト(戻り値ユーザーID)
	 */
	@Test
	void insert_returnId_test() {
		UserIdStatus returnUserId = new UserIdStatus(0);
		
		doReturn(returnUserId).when(this.userDaoSql2).insert_returnId(model);
		Assertions.assertEquals(this.userDaoSql2.insert_returnId(model).getId(), returnUserId.getId());
	}
	
	/**
	 * 更新テスト
	 */
	@Test
	void update_test() {
		Mockito.when(this.jdbcTemp.update(
				"UPDATE chat_user SET name = ?, email = ?, passwd = ?, forgot_passwd = ?, created = ?, updated = ? WHERE id = ?",
				model.getName(),
				model.getEmail(),
				model.getPasswd(),
				model.getForgot_passwd(),
				model.getCreated(),
				model.getUpdated(),
				model.getId()
				)).
			thenReturn(0);
		
		Assertions.assertEquals(this.userDaoSql.update(model), 0);
	}
	
	/**
	 * 更新テスト(パスワード)
	 */
	@Test
	void update_passwd_test() {
		int return_successed = 0;
		
		doReturn(return_successed).when(this.userDaoSql2).update_passwd(user);
		Assertions.assertEquals(this.userDaoSql2.update_passwd(user), return_successed);
	}
	
	/**
	 * 削除テスト(ID)
	 */
	@Test
	void delete_test() {
		UserIdStatus id = new UserIdStatus(0);
		int return_successed = 0;
		
		Mockito.when(this.jdbcTemp.update(
				"DELETE FROM chat_user WHERE id = ?",
				id.getId()
				)).
			thenReturn(return_successed);
		
		Assertions.assertEquals(this.userDaoSql.delete(id), return_successed);
	}

	/**
	 * 全選択テスト
	 */
	@Test
	void getall_test() {
		UserIdStatus userId = new UserIdStatus(0);
		NameWord name       = new NameWord("test");
		EmailWord email     = new EmailWord("test@example.com");
		PasswordWord passwd = new PasswordWord("password");
		PasswordWord forgot = new PasswordWord("forgot");
		
		LocalDateTime time = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(time);
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id",				userId.getId());
		result.put("name",				name.getString());
		result.put("email",				email.getString());
		result.put("passwd",			passwd.getString());
		result.put("forgot_passwd",		forgot.getString());
		result.put("created",			timestamp);
		result.put("updated",			timestamp);
		List<Map<String, Object>> resultList =  new ArrayList<Map<String, Object>>();
		resultList.add(result);
		
		Mockito.when(this.jdbcTemp.queryForList(sql)).
			thenReturn(resultList);
		
		List<UserModel> testList = this.userDaoSql.getAll();
		for( UserModel testModel : testList ) {
			Assertions.assertEquals(testModel.getId(), 				userId.getId());
			Assertions.assertEquals(testModel.getName(), 			name.getString());
			Assertions.assertEquals(testModel.getEmail(), 			email.getString());
			Assertions.assertEquals(testModel.getPasswd(), 			passwd.getString());
			Assertions.assertEquals(testModel.getForgot_passwd(), 	forgot.getString());
			Assertions.assertEquals(testModel.getCreated(), 		time);
			Assertions.assertEquals(testModel.getUpdated(), 		time);
		}
	}
	
	/**
	 * 選択テスト(ID)
	 */
	@Test
	void select_test() {
		UserIdStatus userId = new UserIdStatus(0);
		NameWord name       = new NameWord("test");
		EmailWord email     = new EmailWord("test@example.com");
		PasswordWord passwd = new PasswordWord("password");
		PasswordWord forgot = new PasswordWord("forgot");
		
		LocalDateTime time = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(time);
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user WHERE id = ?";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id",				userId.getId());
		result.put("name",				name.getString());
		result.put("email",				email.getString());
		result.put("passwd",			passwd.getString());
		result.put("forgot_passwd",		forgot.getString());
		result.put("created",			timestamp);
		result.put("updated",			timestamp);
		
		Mockito.when(this.jdbcTemp.queryForMap(sql, userId.getId())).
			thenReturn(result);
		
		UserModel test = this.userDaoSql.select(userId);
		Assertions.assertEquals(test.getId(), 			userId.getId());
		Assertions.assertEquals(test.getName(), 		name.getString());
		Assertions.assertEquals(test.getEmail(), 		email.getString());
		Assertions.assertEquals(test.getPasswd(), 		passwd.getString());
		Assertions.assertEquals(test.getForgot_passwd(), forgot.getString());
		Assertions.assertEquals(test.getCreated(), 		time);
		Assertions.assertEquals(test.getUpdated(), 		time);
	}
	
	/**
	 * 選択テスト_失敗(ID)
	 */
	@Test
	void select_test_failed() {
		UserIdStatus userId = new UserIdStatus(1);
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user WHERE id = ?";
		Mockito.when(this. jdbcTemp.queryForMap(sql, userId.getId())).
			thenThrow(EmptyResultDataAccessException.class);
		
		
		UserModel test = this.userDaoSql.select(userId);
		Assertions.assertEquals(test.getId(), 			0);
		Assertions.assertEquals(test.getName(), 		"");
		Assertions.assertEquals(test.getEmail(), 		"");
		Assertions.assertEquals(test.getPasswd(), 		"");
		Assertions.assertEquals(test.getForgot_passwd(), "");
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * 選択テスト(ログインIDからユーザーIDを取得)
	 */
	@Test
	void select_byId_subLoginId() {
		NameWord name       = new NameWord("test");
		EmailWord email     = new EmailWord("test@example.com");
		PasswordWord passwd = new PasswordWord("password");
		PasswordWord forgot = new PasswordWord("forgot");
		
		LocalDateTime time = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(time);
		int inputId = 0;
		String sql = "SELECT * FROM chat_user WHERE id in"
				+ "("
				+ "SELECT user_id FROM chat_login WHERE id = ?"
				+ ")";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id",				inputId);
		result.put("name",				name.getString());
		result.put("email",				email.getString());
		result.put("passwd",			passwd.getString());
		result.put("forgot_passwd",		forgot.getString());
		result.put("created",			timestamp);
		result.put("updated",			timestamp);
		
		Mockito.when(this.jdbcTemp.queryForMap(sql, inputId)).
			thenReturn(result);
		
		UserModel test = this.userDaoSql.select_byId_subLoginId(new LoginIdStatus(inputId));
		Assertions.assertEquals(test.getId(), 			inputId);
		Assertions.assertEquals(test.getName(), 		name.getString());
		Assertions.assertEquals(test.getEmail(), 		email.getString());
		Assertions.assertEquals(test.getPasswd(), 		passwd.getString());
		Assertions.assertEquals(test.getForgot_passwd(), forgot.getString());
		Assertions.assertEquals(test.getCreated(), 		time);
		Assertions.assertEquals(test.getUpdated(), 		time);
	}
	
	/**
	 * 選択IDテスト(名前、Eメールアドレス、パスワード)
	 * 
	 */
	@Test
	void selectId_byNameEmailPass_test() {
		UserIdStatus userId = new UserIdStatus(0);
		doReturn(userId).when(this.userDaoSql2).selectId_byNameEmailPass(user);
		Assertions.assertEquals(this.userDaoSql2.selectId_byNameEmailPass(user).getId(), userId.getId());
	}
	
	/**
	 * ユーザIDによる有無チェックテスト
	 * 
	 */
	@Test
	void isSelect_byId_test() {
		UserIdStatus testID = new UserIdStatus(0);
		doReturn(true).when(this.userDaoSql2).isSelect_byId(testID);
		Assertions.assertEquals(this.userDaoSql2.isSelect_byId(testID), true);
	}
	
	/**
	 * ユーザ名、Eメールによる有無チェックのテスト
	 * 
	 */
	@Test
	void isSelect_byNameEmail_test() {
		doReturn(true).when(this.userDaoSql2).isSelect_byNameEmail(userEmail);
		Assertions.assertEquals(this.userDaoSql2.isSelect_byNameEmail(userEmail), true);
	}
	
	/**
	 * ユーザ名、Eメール、忘れたとき用パスワードによる有無チェックのテスト
	 */
	@Test
	void isSelect_byNameEmailForgotPW_test() {
		doReturn(true).when(this.userDaoSql2).isSelect_byNameEmailForgotPW(user);
		Assertions.assertEquals(this.userDaoSql2.isSelect_byNameEmailForgotPW(user), true);
	}
	
	/**
	 * ユーザ名、Eメール、忘れたとき用パスワードによる有無チェックのテスト
	 */
	@Test
	void isSelect_byNameEmailPass_test() {
		doReturn(true).when(this.userDaoSql2).isSelect_byNameEmailPass(user);
		Assertions.assertEquals(this.userDaoSql2.isSelect_byNameEmailPass(user), true);
	}
}
