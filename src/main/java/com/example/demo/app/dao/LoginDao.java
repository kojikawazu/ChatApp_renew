package com.example.demo.app.dao;

import java.util.List;

import com.example.demo.app.entity.LoginModel;

/**
 * ログインDaoパターン
 *
 */
public interface LoginDao {
	
	void insert(LoginModel model);
	
	int insert_byId(LoginModel model);
	
	int update(LoginModel model);
	
	int updateRoomId_byId(int roomId, int id);
	
	int updateRoomId_byUserId(int roomId, int userId);
	
	int updateRoomId_byRoomId(int roomId, int changeId);
	
	int delete(int id);
	
	List<LoginModel> getAll();
	
	LoginModel select(int id);
	
	List<LoginModel> selectList_byRoomId(int roomId);
	
	int selectId_byUserId(int userId);
	
	LoginModel select_byuserId(int userId);
	
	int selectRoomId_byUserId(int userId);
	
	boolean isSelect_byId(int id);
	
	boolean isSelect_byUserId(int userId);

}
