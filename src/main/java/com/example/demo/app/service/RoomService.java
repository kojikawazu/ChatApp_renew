package com.example.demo.app.service;

import java.util.List;

import com.example.demo.app.entity.RoomModel;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ルームサービス(インターフェース)
 *
 */
public interface RoomService {

	void save(RoomModel model);
	
	int save_returnId(RoomModel model);
	
	void update(RoomModel model);
	
	void updateUserId_byUserId(UserIdStatus userId, UserIdStatus newId);
	
	void delete(RoomIdStatus id);
	
	List<RoomModel> getAll();
	
	RoomModel select(RoomIdStatus id);
	
	boolean isSelect_byId(RoomIdStatus id);
}
