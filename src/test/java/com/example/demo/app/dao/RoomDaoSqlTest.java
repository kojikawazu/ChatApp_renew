package com.example.demo.app.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.UserModel;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
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
	
	private RoomModel model;								/** ルームモデル */
	
	private LocalDateTime created = LocalDateTime.now();	/** 生成日時  */
	private LocalDateTime updated = LocalDateTime.now();	/** 更新日時  */

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
}
