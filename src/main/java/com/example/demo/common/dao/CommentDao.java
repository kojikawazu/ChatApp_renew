package com.example.demo.common.dao;

import java.util.List;

import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.CommentModelEx;
import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.RoomIdStatus;

/**
 * 【コメントDaoパターン】(インターフェース)
 * 
 */
public interface CommentDao {
	
	void insert(CommentModel model);
	
	CommentIdStatus insert_byId(CommentModel model);
	
	int update(CommentModel model);
	
	int delete(CommentIdStatus id);
	
	int delete_byRoomId(RoomIdStatus roomId);
	
	List<CommentModel> getAll();
	
	List<CommentModelEx> getAll_plusUserName();
	
	CommentModel select(CommentIdStatus id);
	
	List<CommentModel> select_byRoomId(RoomIdStatus roomId);
	
	List<CommentModelEx> select_plusUserName_byRoomId(RoomIdStatus roomId);
	
	boolean isSelect_byId(CommentIdStatus id);
}
