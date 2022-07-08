package com.example.demo.app.entity;

import java.time.LocalDateTime;

import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ログインモデル
 * @author nanai
 *
 */
public class LoginModel {

	/**
	 * フィールド
	 * 
	 */
	private LoginIdStatus id;				/** ログインID */
	private RoomIdStatus room_id;			/** ルームID  */
	private UserIdStatus user_id;			/** ユーザID  */
	private LocalDateTime created;			/** 生成日時 */
	
	/**
	 * コンストラクタ
	 * @param model: ログインモデルクラス
	 */
	public LoginModel(LoginModel model) {
		super();

		if( model == null ) {
			this.id 		= new LoginIdStatus(0);
			this.room_id 	= new RoomIdStatus(0);
			this.user_id 	= new UserIdStatus(0);
			this.created 	= LocalDateTime.now();
		} else {
			this.id 		= new LoginIdStatus(model.getId());
			this.room_id 	= new RoomIdStatus(model.getRoom_id());
			this.user_id 	= new UserIdStatus(model.getUser_id());
			this.created 	= model.getCreated();
		}
	}
	
	/**
	 * コンストラクタ
	 * @param id: ログインID
	 * @param room_id: ルームID
	 * @param user_id: ユーザーID
	 * @param created: 生成日付
	 */
	public LoginModel(LoginIdStatus id,
			RoomIdStatus room_id,
			UserIdStatus user_id,
			LocalDateTime created) {
		super();
		
		this.id = (id == null ? 
				new LoginIdStatus(0) :
				new LoginIdStatus(id.getId()));
		
		this.room_id = (room_id == null ?
				new RoomIdStatus(0) :
				new RoomIdStatus(room_id.getId())
				);
		
		this.user_id = (user_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(user_id.getId()));

		this.created 	= created;
	}
	
	/**
	 * コンストラクタ
	 * @param room_id: ルームID
	 * @param user_id: ユーザーID
	 * @param created: 生成日付
	 */
	public LoginModel(RoomIdStatus room_id,
			UserIdStatus user_id,
			LocalDateTime created) {
		super();
		this.id = new LoginIdStatus(0);
		
		this.room_id = (room_id == null ?
				new RoomIdStatus(0) :
				new RoomIdStatus(room_id.getId())
				);
		
		this.user_id = (user_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(user_id.getId()));

		this.created 	= created;
	}

	public int getId() {
		return id.getId();
	}

	protected void setId(int id) {
		this.id = new LoginIdStatus(id);
	}

	public int getRoom_id() {
		return room_id.getId();
	}

	protected void setRoom_id(int room_id) {
		this.room_id = new RoomIdStatus(room_id);
	}

	public int getUser_id() {
		return user_id.getId();
	}

	protected void setUser_id(int user_id) {
		this.user_id = new UserIdStatus(user_id);
	}

	public LocalDateTime getCreated() {
		return created;
	}

	protected void setCreated(LocalDateTime created) {
		this.created = created;
	}
}
