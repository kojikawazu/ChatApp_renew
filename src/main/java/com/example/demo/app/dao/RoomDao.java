package com.example.demo.app.dao;

import java.util.List;

import com.example.demo.app.entity.RoomModel;

/**
 * ルームDaoパターン
 *
 */
public interface RoomDao {
	
	void insert(RoomModel model);
	
	int insert_byId(RoomModel model);
	
	int update(RoomModel model);
	
	int updateUserId_byUserId(int userId, int newId);
	
	int delete(int id);
	
	List<RoomModel> getAll();
	
	RoomModel select(int id);
	
	boolean isSelect_byId(int id);

}
