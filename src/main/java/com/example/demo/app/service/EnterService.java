package com.example.demo.app.service;

import java.util.List;

import com.example.demo.app.entity.EnterModel;

/**
 * 入室サービス
 *
 */
public interface EnterService {
	
	void save(EnterModel model);
	
	int save_returnId(EnterModel model);
	
	void update(EnterModel model);
	
	void update_byUserId(int room_id, int manager_id, int sum, int user_id);
	
	void updateManagerId_byId(int managerId, int id);
	
	void delete(int id);
	
	List<EnterModel> getAll();
	
	EnterModel select(int id);
	
	int selectId_byUserId(int userId);
	
	boolean isSelect_byId(int id);
	
	boolean isSelect_byUserId(int userId);
	
	int getCount_roomId(int roomId);

}
