package com.example.demo.app.entity;

import java.time.LocalDateTime;

/**
 * ログインモデル
 *
 */
public class LoginModel {

	private int id;							// ログインID
	private int room_id;					// ルームID
	private int user_id;					// ユーザID
	private LocalDateTime created;			// 生成日時
	
	/**
	 * コンストラクタ
	 * @param id
	 * @param room_id
	 * @param user_id
	 * @param created
	 */
	public LoginModel(
			int id,
			int room_id,
			int user_id,
			LocalDateTime created) {
		super();
		this.id 		= id;
		this.room_id 	= room_id;
		this.user_id 	= user_id;
		this.created 	= created;
	}
	
	/**
	 * コンストラクタ
	 * @param model
	 */
	public LoginModel(LoginModel model) {
		super();

		if( model == null ) {
			this.id 		= 0;
			this.room_id 	= 0;
			this.user_id 	= 0;
			this.created 	= LocalDateTime.now();
		} else {
			this.id 		= model.getId();
			this.room_id 	= model.getRoom_id();
			this.user_id 	= model.getUser_id();
			this.created 	= model.getCreated();
		}
	}

	public int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	public int getRoom_id() {
		return room_id;
	}

	protected void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public int getUser_id() {
		return user_id;
	}

	protected void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	protected void setCreated(LocalDateTime created) {
		this.created = created;
	}
}
