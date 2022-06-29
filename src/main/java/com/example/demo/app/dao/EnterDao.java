package com.example.demo.app.dao;

import java.util.List;

import com.example.demo.app.entity.EnterModel;

/**
 * 入室Daoパターン
 *
 */
public interface EnterDao {
	
	void insert(EnterModel model);
	
	int insert_byId(EnterModel model);
	
	int update(EnterModel model);
	
	int update_byUserId(int room_id, int manager_id, int sum, int user_id);
	
	int updateManagerId_byId(int managerId, int id);
	
	int delete(int id);
	
	List<EnterModel> getAll();
	
	EnterModel select(int id);
	
	int selectId_byUserId(int userId);

	boolean isSelect_byId(int id);
	
	boolean isSelect_byUserId(int userId);

	int getCount_roomId(int roomId);
	
}
