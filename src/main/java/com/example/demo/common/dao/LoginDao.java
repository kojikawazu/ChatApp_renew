package com.example.demo.common.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.LoginModelEx;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ログインDaoパターン
 *
 */
public interface LoginDao {
	
	void insert(LoginModel model);
	
	LoginIdStatus insert_returnId(LoginModel model);
	
	int update(LoginModel model);
	
	int updateRoomId_byId(RoomIdStatus roomId, LoginIdStatus id);
	
	int updateRoomId_byUserId(RoomIdStatus roomId, UserIdStatus userId);
	
	int updateRoomId_byRoomId(RoomIdStatus roomId, RoomIdStatus changeId);
	
	int updateUpdated_byId(LocalDateTime updated, LoginIdStatus id);
	
	int delete(LoginIdStatus id);
	
	List<LoginModel> getAll();
	
	LoginModel select(LoginIdStatus id);
	
	List<LoginModel> selectList_byRoomId(RoomIdStatus roomId);
	
	List<LoginModelEx> selectList_plusUserName_byRoomId(RoomIdStatus roomId);
	
	LoginIdStatus selectId_byUserId(UserIdStatus userId);
	
	LoginModel select_byuserId(UserIdStatus userId);
	
	RoomIdStatus selectRoomId_byUserId(UserIdStatus userId);
	
	boolean isSelect_byId(LoginIdStatus id);
	
	boolean isSelect_byUserId(UserIdStatus userId);
}
