package com.example.demo.common.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.app.entity.EnterModel;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 入室Daoパターン(インターフェース)
 *
 */
public interface EnterDao {
	
	void insert(EnterModel model);
	
	EnterIdStatus insert_byId(EnterModel model);
	
	int update(EnterModel model);
	
	int update_byUserId(RoomIdStatus room_id, UserIdStatus manager_id, UserIdStatus user_id);
	
	int updateManagerId_byId(UserIdStatus managerId, EnterIdStatus id);
	
	int updateUpdated_byId(LocalDateTime updated, EnterIdStatus id);
	
	int delete(EnterIdStatus id);
	
	int delete_byUserId(UserIdStatus user_id);
	
	List<EnterModel> getAll();
	
	EnterModel select(EnterIdStatus id);
	
	EnterIdStatus selectId_byUserId(UserIdStatus userId);

	boolean isSelect_byId(EnterIdStatus id);
	
	boolean isSelect_byUserId(UserIdStatus userId);

	int getCount_roomId(RoomIdStatus roomId);
}
