package com.example.demo.app.service;

import java.util.List;

import com.example.demo.app.entity.CommentModel;
import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.RoomIdStatus;

/**
 * コメントサービス(インターフェース
 *
 */
public interface CommentService {
	
	void save(CommentModel model);
	
	int save_returnId(CommentModel model);
	
	void update(CommentModel model);
	
	void delete(CommentIdStatus id);
	
	void delete_byRoomId(RoomIdStatus roomId);
	
	List<CommentModel> getAll();
	
	List<CommentModel> select_byRoomId(RoomIdStatus roomId);
	
	CommentModel select(CommentIdStatus id);
	
	boolean isSelect_byId(CommentIdStatus id);
}
