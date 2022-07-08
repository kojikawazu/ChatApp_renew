package com.example.demo.common.dao;

import java.util.List;

import com.example.demo.app.entity.RoomModel;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ルームDaoパターン(インターフェース)
 * @author nanai
 *
 */
public interface RoomDao {
	
	void insert(RoomModel model);
	
	int insert_byId(RoomModel model);
	
	int update(RoomModel model);
	
	int updateUserId_byUserId(UserIdStatus userId, UserIdStatus newId);
	
	int delete(RoomIdStatus id);
	
	List<RoomModel> getAll();
	
	RoomModel select(RoomIdStatus id);
	
	boolean isSelect_byId(RoomIdStatus id);
}
