package com.example.demo.app.entity;

import java.time.LocalDateTime;

/**
 * コメントモデル
 *
 */
public class CommentModel {
	
	private int id;					// コメントID
	private String comment;			// コメント
	private int room_id;				// ルームID
	private int user_id;				// ユーザID
	private LocalDateTime created;		// 生成日時
	
	public CommentModel() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}	
	
	
}
