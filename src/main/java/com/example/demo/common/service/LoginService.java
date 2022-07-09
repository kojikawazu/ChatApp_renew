package com.example.demo.common.service;

import java.util.List;

import com.example.demo.app.entity.LoginModel;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ログインサービス（インターフェース)
 *
 */
public interface LoginService {
	
	void save(LoginModel model);
	
	LoginIdStatus save_returnId(LoginModel model);
	
	void update(LoginModel model);
	
	void updateRoomId_byId(RoomIdStatus roomId, LoginIdStatus id);
	
	void updateRoomId_byUserId(RoomIdStatus roomId, UserIdStatus userId);
	
	void updateRoomId_byRoomId(RoomIdStatus roomId, RoomIdStatus changeId);
	
	void delete(LoginIdStatus id);
	
	List<LoginModel> getAll();
	
	LoginModel select(LoginIdStatus id);
	
	List<LoginModel> selectList_byRoomId(RoomIdStatus roomId);
	
	LoginIdStatus selectId_byUserId(UserIdStatus userId);
	
	LoginModel select_byUserId(UserIdStatus userId);
	
	RoomIdStatus selectRoomId_byUserId(UserIdStatus userId);
	
	boolean isSelect_byId(LoginIdStatus id);
	
	boolean isSelect_byUserId(UserIdStatus userId);
}
