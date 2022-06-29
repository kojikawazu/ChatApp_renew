package com.example.demo.app.entity;

import java.time.LocalDateTime;

/**
 * ルームモデル
 *
 */
public class RoomModel {
	
	private int id;					// ルームID
	private String name;				// ルーム名
	private String comment;			// コメント
	private String tag;				// タグ
	private int max_roomsum;			// 入室最大数
	private int user_id;				// ホストID
	private LocalDateTime created;		// 生成日時
	private LocalDateTime updated;		// 更新日時
	
	public RoomModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getMax_roomsum() {
		return max_roomsum;
	}

	public void setMax_roomsum(int max_roomsum) {
		this.max_roomsum = max_roomsum;
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

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
	
	
	

}
