package com.example.demo.app.service;

import java.util.List;

import com.example.demo.app.entity.RoomModel;

/**
 * ルームサービス
 *
 */
public interface RoomService {

	void save(RoomModel model);
	
	int save_returnId(RoomModel model);
	
	void update(RoomModel model);
	
	void updateUserId_byUserId(int userId, int newId);
	
	void delete(int id);
	
	List<RoomModel> getAll();
	
	RoomModel select(int id);
	
	boolean isSelect_byId(int id);
	
}
