package com.example.demo.common.service;

import java.util.List;

import com.example.demo.app.entity.EnterModel;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 入室サービス(インターフェース)
 *
 */
public interface EnterService {
	
	void save(EnterModel model);
	
	int save_returnId(EnterModel model);
	
	void update(EnterModel model);
	
	void update_byUserId(RoomIdStatus room_id, UserIdStatus manager_id, RoomMaxNumber sum, UserIdStatus user_id);
	
	void updateManagerId_byId(UserIdStatus managerId, EnterIdStatus id);
	
	void delete(EnterIdStatus id);
	
	List<EnterModel> getAll();
	
	EnterModel select(EnterIdStatus id);
	
	int selectId_byUserId(UserIdStatus userId);
	
	boolean isSelect_byId(EnterIdStatus id);
	
	boolean isSelect_byUserId(UserIdStatus userId);
	
	int getCount_roomId(RoomIdStatus roomId);
}
