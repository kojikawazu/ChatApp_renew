package com.example.demo.common.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.RoomModelEx;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ルームサービス(インターフェース)
 *
 */
public interface RoomService {

	void save(RoomModel model);
	
	RoomIdStatus save_returnId(RoomModel model);
	
	void update(RoomModel model);
	
	void updateUserId_byUserId(UserIdStatus userId, UserIdStatus newId);
	
	void updateUpdated_byId(LocalDateTime updated, RoomIdStatus id);
	
	void delete(RoomIdStatus id);
	
	List<RoomModel> getAll();
	
	List<RoomModelEx> getAll_plusUserName_EnterCnt();
	
	RoomModel select(RoomIdStatus id);
	
	RoomModelEx select_plusUserName_EnterCnt(RoomIdStatus id);
	
	boolean isSelect_byId(RoomIdStatus id);
}
