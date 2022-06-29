package com.example.demo.app.dao;

import java.util.List;

import com.example.demo.app.entity.CommentModel;

/**
 * 【コメントDaoパターン】
 */
public interface CommentDao {
	
	void insert(CommentModel model);
	
	int insert_byId(CommentModel model);
	
	int update(CommentModel model);
	
	int delete(int id);
	
	int delete_byRoomId(int roomId);
	
	List<CommentModel> getAll();
	
	CommentModel select(int id);
	
	List<CommentModel> select_byRoomId(int roomId);
	
	boolean isSelect_byId(int id);

}
