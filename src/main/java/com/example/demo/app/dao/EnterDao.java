package com.example.demo.app.dao;

import java.util.List;

import com.example.demo.app.entity.EnterModel;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 入室Daoパターン(インターフェース)
 *
 */
public interface EnterDao {
	
	void insert(EnterModel model);
	
	int insert_byId(EnterModel model);
	
	int update(EnterModel model);
	
	int update_byUserId(RoomIdStatus room_id, UserIdStatus manager_id, RoomMaxNumber sum, UserIdStatus user_id);
	
	int updateManagerId_byId(UserIdStatus managerId, EnterIdStatus id);
	
	int delete(EnterIdStatus id);
	
	List<EnterModel> getAll();
	
	EnterModel select(EnterIdStatus id);
	
	int selectId_byUserId(UserIdStatus userId);

	boolean isSelect_byId(EnterIdStatus id);
	
	boolean isSelect_byUserId(UserIdStatus userId);

	int getCount_roomId(RoomIdStatus roomId);
}
