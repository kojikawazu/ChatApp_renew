package com.example.demo.app.service;

import java.util.List;

import com.example.demo.app.entity.CommentModel;

/**
 * コメントサービス
 *
 */
public interface CommentService {
	
	void save(CommentModel model);
	
	int save_returnId(CommentModel model);
	
	void update(CommentModel model);
	
	void delete(int id);
	
	void delete_byRoomId(int roomId);
	
	List<CommentModel> getAll();
	
	List<CommentModel> select_byRoomId(int roomId);
	
	CommentModel select(int id);
	
	boolean isSelect_byId(int id);
	
}
