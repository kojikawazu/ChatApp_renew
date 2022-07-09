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
	
	private UserModel model;									/** ユーザーモデル	*/
	private UserNameEmailPassword user;							/** ユーザーフォーム	*/
	private UserNameEmail userEmail;							/** ユーザーフォーム2	*/
	
	private LocalDateTime created = LocalDateTime.now();		/** 生成日時     */
	private LocalDateTime updated = LocalDateTime.now();		/** 更新日時     */
	
	@BeforeEach
	void before() {
		this.model = new UserModel(
				new UserIdStatus(0),
				new NameWord("test"),
				new EmailWord("test@example.com"),
				new PasswordWord("password"),
				new PasswordWord("forgot"),
				created,
				updated);
		
		this.user = new UserNameEmailPassword(
				new NameWord("test"), 
				new EmailWord("test@example.com"), 
				new PasswordWord("password"));
		
		this.userEmail = new UserNameEmail(
				new NameWord("test"), 
				new EmailWord("test@example.com"));
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
		doReturn(new UserIdStatus(0)).when(this.userDaoSql2).insert_returnId(model);
		Assertions.assertEquals(this.userDaoSql2.insert_returnId(model).getId(), 0);
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
		doReturn(0).when(this.userDaoSql2).update_passwd(user);
		Assertions.assertEquals(this.userDaoSql2.update_passwd(user), 0);
	}
	
	/**
	 * 削除テスト(ID)
	 */
	@Test
	void delete_test() {
		UserIdStatus id = new UserIdStatus(0);
		
		Mockito.when(this.jdbcTemp.update(
				"DELETE FROM chat_user WHERE id = ?",
				id.getId()
				)).
			thenReturn(0);
		
		Assertions.assertEquals(this.userDaoSql.delete(id), 0);
	}

	/**
	 * 全選択テスト
	 */
	@Test
	void getall_test() {
		LocalDateTime time = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(time);
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id",				0);
		result.put("name",				"name");
		result.put("email",				"test@example.com");
		result.put("passwd",			"password");
		result.put("forgot_passwd",		"password");
		result.put("created",			timestamp);
		result.put("updated",			timestamp);
		List<Map<String, Object>> resultList =  new ArrayList<Map<String, Object>>();
		resultList.add(result);
		
		Mockito.when(this.jdbcTemp.queryForList(sql)).
			thenReturn(resultList);
		
		List<UserModel> testList = this.userDaoSql.getAll();
		for( UserModel testModel : testList ) {
			Assertions.assertEquals(testModel.getId(), 				0);
			Assertions.assertEquals(testModel.getName(), 			"name");
			Assertions.assertEquals(testModel.getEmail(), 			"test@example.com");
			Assertions.assertEquals(testModel.getPasswd(), 			"password");
			Assertions.assertEquals(testModel.getForgot_passwd(), 	"password");
			Assertions.assertEquals(testModel.getCreated(), 		time);
			Assertions.assertEquals(testModel.getUpdated(), 		time);
		}
	}
	
	/**
	 * 選択テスト(ID)
	 */
	@Test
	void select_test() {
		LocalDateTime time = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(time);
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user WHERE id = ?";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id",				0);
		result.put("name",				"name");
		result.put("email",				"test@example.com");
		result.put("passwd",			"password");
		result.put("forgot_passwd",		"password");
		result.put("created",			timestamp);
		result.put("updated",			timestamp);
		
		Mockito.when(this.jdbcTemp.queryForMap(sql, 0)).
			thenReturn(result);
		
		UserModel test = this.userDaoSql.select(new UserIdStatus(0));
		Assertions.assertEquals(test.getId(), 			0);
		Assertions.assertEquals(test.getName(), 		"name");
		Assertions.assertEquals(test.getEmail(), 		"test@example.com");
		Assertions.assertEquals(test.getPasswd(), 		"password");
		Assertions.assertEquals(test.getForgot_passwd(), "password");
		Assertions.assertEquals(test.getCreated(), 		time);
		Assertions.assertEquals(test.getUpdated(), 		time);
	}
	
	/**
	 * 選択テスト_失敗(ID)
	 */
	@Test
	void select_test_failed() {
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user WHERE id = ?";
		Mockito.when(this. jdbcTemp.queryForMap(sql, 1)).
			thenThrow(EmptyResultDataAccessException.class);
		
		
		UserModel test = this.userDaoSql.select(new UserIdStatus(1));
		Assertions.assertEquals(test.getId(), 			0);
		Assertions.assertEquals(test.getName(), 		"");
		Assertions.assertEquals(test.getEmail(), 		"");
		Assertions.assertEquals(test.getPasswd(), 		"");
		Assertions.assertEquals(test.getForgot_passwd(), "");
		Assertions.assertNotNull(test.getCreated());
		Assertions.assertNotNull(test.getUpdated());
	}
	
	/**
	 * 選択IDテスト(名前、Eメールアドレス、パスワード)
	 * 
	 */
	@Test
	void selectId_byNameEmailPass_test() {
		doReturn(new UserIdStatus(0)).when(this.userDaoSql2).selectId_byNameEmailPass(user);
		Assertions.assertEquals(this.userDaoSql2.selectId_byNameEmailPass(user).getId(), 0);
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
