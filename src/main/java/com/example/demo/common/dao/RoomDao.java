package com.example.demo.common.dao;

import java.util.List;

import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.RoomModelEx;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ルームDaoパターン(インターフェース)
 * @author nanai
 *
 */
public interface RoomDao {
	
	void insert(RoomModel model);
	
	RoomIdStatus insert_returnId(RoomModel model);
	
	int update(RoomModel model);
	
	int updateUserId_byUserId(UserIdStatus userId, UserIdStatus newId);
	
	int delete(RoomIdStatus id);
	
	List<RoomModel> getAll();
	
	List<RoomModelEx> getAll_plusUserName_EnterCnt();
	
	RoomModel select(RoomIdStatus id);
	
	RoomModelEx select_plusUserName(RoomIdStatus id);
	
	boolean isSelect_byId(RoomIdStatus id);
}
