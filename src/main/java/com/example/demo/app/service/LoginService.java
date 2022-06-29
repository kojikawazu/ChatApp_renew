package com.example.demo.app.service;

import java.util.List;

import com.example.demo.app.entity.LoginModel;

/**
 * ログインサービス
 *
 */
public interface LoginService {
	
	void save(LoginModel model);
	
	int save_returnId(LoginModel model);
	
	void update(LoginModel model);
	
	void updateRoomId_byId(int roomId, int id);
	
	void updateRoomId_byUserId(int roomId, int userId);
	
	void updateRoomId_byRoomId(int roomId, int changeId);
	
	void delete(int id);
	
	List<LoginModel> getAll();
	
	LoginModel select(int id);
	
	List<LoginModel> selectList_byRoomId(int roomId);
	
	int selectId_byUserId(int userId);
	
	LoginModel select_byUserId(int userId);
	
	int selectRoomId_byUserId(int userId);
	
	boolean isSelect_byId(int id);
	
	boolean isSelect_byUserId(int userId);

}
