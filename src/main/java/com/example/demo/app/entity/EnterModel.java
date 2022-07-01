package com.example.demo.app.entity;

import java.time.LocalDateTime;

/**
 * 入室モデル
 *
 */
public class EnterModel {
	
	private int id;						// 入室ID
	private int room_id;				// ルームID
	private int user_id;				// ユーザID
	private int manager_id;				// ホストID
	private int max_sum;				// 入室数
	private LocalDateTime created;		// 生成日時
	
	public EnterModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}

	public int getMax_sum() {
		return max_sum;
	}

	public void setMax_sum(int max_sum) {
		this.max_sum = max_sum;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	
	

}
