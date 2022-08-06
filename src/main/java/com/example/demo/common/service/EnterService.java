package com.example.demo.common.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.app.entity.EnterModel;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 入室サービス(インターフェース)
 *
 */
public interface EnterService {
	
	void save(EnterModel model);
	
	EnterIdStatus save_returnId(EnterModel model);
	
	LocalDateTime update(EnterModel model);
	
	LocalDateTime update_byUserId(RoomIdStatus room_id, UserIdStatus manager_id, UserIdStatus user_id);
	
	LocalDateTime updateManagerId_byId(UserIdStatus managerId, EnterIdStatus id);
	
	void updateUpdated_byId(LocalDateTime updated, EnterIdStatus id);
	
	void delete(EnterIdStatus id);
	
	void delete_byUserId(UserIdStatus user_id);
	
	List<EnterModel> getAll();
	
	EnterModel select(EnterIdStatus id);
	
	EnterIdStatus selectId_byUserId(UserIdStatus userId);
	
	boolean isSelect_byId(EnterIdStatus id);
	
	boolean isSelect_byUserId(UserIdStatus userId);
	
	int getCount_roomId(RoomIdStatus roomId);
}
