package com.example.demo.app.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.RoomModelEx;
import com.example.demo.app.entity.UserModel;
import com.example.demo.common.dao.RoomDaoSql;
import com.example.demo.common.number.RoomEnterCntNumber;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.PasswordWord;
import com.example.demo.common.word.RoomCommentWord;
import com.example.demo.common.word.RoomNameWord;
import com.example.demo.common.word.RoomTagWord;

/**
 * ルームへのDBアクセスクラスのテスト
 * @author nanai
 *
 */
@ExtendWith(MockitoExtension.class)
class RoomDaoSqlTest {

	/**
	 * フィールド
	 * 
	 */
	@Mock
	private JdbcTemplate jdbcTemp;
	
	@InjectMocks
	private RoomDaoSql roomDaoSql;
	
	@Mock
	private RoomDaoSql roomDaoSql2;
	
	/** ルームモデル */
	private RoomModel model;
	
	/** 生成日時  */
	private LocalDateTime created = LocalDateTime.now();
	
	/** 更新日時  */
	private LocalDateTime updated = LocalDateTime.now();

	/**
	 * 準備
	 */
	@BeforeEach
	void before() {
		model = new RoomModel(
				new RoomIdStatus(0),
				new RoomNameWord("name"),
				new RoomCommentWord("comment"),
				new RoomTagWord("tag"),
				new RoomMaxNumber(4),
				new UserIdStatus(1),
				created,
				updated);
	}
	/**
	 * 追加のテスト
	 * 
	 */
	@Test
	void insert_test() {
		doNothing().when(this.roomDaoSql2).insert(model);
		this.roomDaoSql2.insert(model);
		verify(roomDaoSql2, times(1)).insert(model);
	}
	
	/**
	 * 追加_jdbcTempleteの呼び出し回数テスト
	 * 
	 */
	@Test
	void insert_jdbcTemp_test() {
		this.roomDaoSql.insert(model);
		verify(jdbcTemp, times(1)).update(
				ArgumentMatchers.anyString(), 
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyInt(),
				ArgumentMatchers.any(LocalDateTime.class),
				ArgumentMatchers.any(LocalDateTime.class));
	}
	
	/**
	 * 追加_jdbcTempleteの呼び出し回数テスト2
	 * 
	 */
	@Test
	void insert_jdbcTemp_null_test() {
		this.roomDaoSql.insert(null);
		verify(jdbcTemp, times(0)).update(
				ArgumentMatchers.anyString(), 
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyInt(),
				ArgumentMatchers.any(LocalDateTime.class),
				ArgumentMatchers.any(LocalDateTime.class));
	}
	
	/**
	 * 追加テスト(戻り値ルームID)
	 */
	@Test
	void insert_returnId_test() {
		RoomIdStatus returnRoomId = new RoomIdStatus(0);
		
		doReturn(returnRoomId).when(this.roomDaoSql2).insert_returnId(model);
		Assertions.assertEquals(this.roomDaoSql2.insert_returnId(model).getId(), returnRoomId.getId());
	}
	
	/**
	 * 更新テスト
	 */
	@Test
	void update_test() {
		Mockito.when(this.jdbcTemp.update(
				"UPDATE chat_room SET name = ?, comment = ?, tag = ?, max_roomsum = ?, user_id = ?, created = ?, updated = ? WHERE id = ?",
				model.getName(),
				model.getComment(),
				model.getTag(),
				model.getMax_roomsum(),
				model.getUser_id(),
				model.getCreated(),
				model.getUpdated(),
				model.getId()
				)).
			thenReturn(0);
		
		Assertions.assertEquals(this.roomDaoSql.update(model), 0);
	}
	
	/**
	 * 更新テスト(
	 */
	@Test
	void updateUserId_byUserId_test() {
		UserIdStatus userId = new UserIdStatus(1); 
		UserIdStatus newId  = new UserIdStatus(2);
		Mockito.when(this.jdbcTemp.update(
				"UPDATE chat_room SET user_id = ? WHERE user_id = ?",
				newId.getId(), 
				userId.getId())).
			thenReturn(0);
		
		Assertions.assertEquals(this.roomDaoSql.updateUserId_byUserId(userId, newId), 0);
	}
	
	/**
	 * 削除テスト(ID)
	 */
	@Test
	void delete_test() {
		RoomIdStatus id = new RoomIdStatus(1);
		
		Mockito.when(this.jdbcTemp.update(
				"DELETE FROM chat_room WHERE id = ?",
				id.getId())).
			thenReturn(0);
		
		Assertions.assertEquals(this.roomDaoSql.delete(id), 0);
	}
	
	/**
	 * 全選択テスト
	 */
	@Test
	void getall_test() {
		RoomIdStatus id         = new RoomIdStatus(0);
		RoomNameWord name       = new RoomNameWord("name");
		RoomCommentWord comment = new RoomCommentWord("comment");
		RoomTagWord tag         = new RoomTagWord("tag");
		RoomMaxNumber max       = new RoomMaxNumber(3);
		UserIdStatus userId     = new UserIdStatus(2);
		
		LocalDateTime time = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(time);
		String sql = "SELECT id, name, comment, tag, max_roomsum, user_id, created, updated "
				+ "FROM chat_room ORDER BY updated DESC";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id",				id.getId());
		result.put("name",				name.getString());
		result.put("comment",			comment.getString());
		result.put("tag", 				tag.getString());
		result.put("max_roomsum",		max.getNumber());
		result.put("user_id",			userId.getId());
		result.put("created",			timestamp);
		result.put("updated",			timestamp);
		List<Map<String, Object>> resultList =  new ArrayList<Map<String, Object>>();
		resultList.add(result);
		
		Mockito.when(this.jdbcTemp.queryForList(sql)).
			thenReturn(resultList);
		
		List<RoomModel> testList = this.roomDaoSql.getAll();
		for( RoomModel testModel : testList ) {
			Assertions.assertEquals(testModel.getId(), 				id.getId());
			Assertions.assertEquals(testModel.getName(), 			name.getString());
			Assertions.assertEquals(testModel.getComment(), 		comment.getString());
			Assertions.assertEquals(testModel.getTag(), 			tag.getString());
			Assertions.assertEquals(testModel.getMax_roomsum(), 	max.getNumber());
			Assertions.assertEquals(testModel.getUser_id(), 		userId.getId());
			Assertions.assertEquals(testModel.getCreated(), 		time);
			Assertions.assertEquals(testModel.getUpdated(), 		time);
		}
	}
	
	/**
	 * 全選択+ユーザー名+入室数の選択テスト
	 */
	@Test
	void getAll_plusUserName_EnterCnt() {
		RoomIdStatus id             = new RoomIdStatus(0);
		RoomNameWord name           = new RoomNameWord("name");
		RoomCommentWord comment     = new RoomCommentWord("comment");
		RoomTagWord tag             = new RoomTagWord("tag");
		RoomMaxNumber max           = new RoomMaxNumber(3);
		UserIdStatus userId         = new UserIdStatus(2);
		NameWord userName           = new NameWord("userName");
		RoomEnterCntNumber enterCnt = new RoomEnterCntNumber(1);
		
		LocalDateTime time = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(time);
		String sql = "SELECT chat_room.*,chat_user.name AS user_name,"
				+ "CAST(("
				+ "SELECT COUNT(*) FROM chat_enter WHERE chat_enter.room_id = chat_room.id"
				+ ") AS INTEGER) AS enter_cnt "
				+ "FROM chat_room LEFT OUTER JOIN chat_user ON "
				+ "chat_room.user_id = chat_user.id";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id",				id.getId());
		result.put("name",				name.getString());
		result.put("comment",			comment.getString());
		result.put("tag", 				tag.getString());
		result.put("max_roomsum",		max.getNumber());
		result.put("user_id",			userId.getId());
		result.put("user_name", 		userName.getString());
		result.put("enter_cnt",	 		enterCnt.getNumber());
		result.put("created",			timestamp);
		result.put("updated",			timestamp);
		List<Map<String, Object>> resultList =  new ArrayList<Map<String, Object>>();
		resultList.add(result);
		
		Mockito.when(this.jdbcTemp.queryForList(sql)).
			thenReturn(resultList);
		
		List<RoomModelEx> testList = this.roomDaoSql.getAll_plusUserName_EnterCnt();
		for( RoomModelEx testModel : testList ) {
			Assertions.assertEquals(testModel.getId(), 				id.getId());
			Assertions.assertEquals(testModel.getName(), 			name.getString());
			Assertions.assertEquals(testModel.getComment(), 		comment.getString());
			Assertions.assertEquals(testModel.getTag(), 			tag.getString());
			Assertions.assertEquals(testModel.getMax_roomsum(), 	max.getNumber());
			Assertions.assertEquals(testModel.getUser_id(), 		userId.getId());
			Assertions.assertEquals(testModel.getUserName(), 		userName.getString());
			Assertions.assertEquals(testModel.getEnterCnt(), 		enterCnt.getNumber());
			Assertions.assertEquals(testModel.getCreated(), 		time);
			Assertions.assertEquals(testModel.getUpdated(), 		time);
		}
	}
	
	/**
	 * ユーザIDによる有無チェックテスト
	 * 
	 */
	@Test
	void isSelect_byId_test() {
		RoomIdStatus testID = new RoomIdStatus(0);
		doReturn(true).when(this.roomDaoSql2).isSelect_byId(testID);
		Assertions.assertEquals(this.roomDaoSql2.isSelect_byId(testID), true);
	}
}
