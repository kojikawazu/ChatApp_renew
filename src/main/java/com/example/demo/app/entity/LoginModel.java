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
public class LoginModel implements SuperModel {

	/**
	 * フィールド
	 * 
	 */
	private LoginIdStatus id;				/** ログインID */
	private RoomIdStatus room_id;			/** ルームID  */
	private UserIdStatus user_id;			/** ユーザID  */
	private LocalDateTime created;			/** 生成日時 */
	private LocalDateTime updated;			/** 更新日時 */
	
	/**
	 * コンストラクタ
	 * @param id      ログインID
	 * @param room_id ルームID
	 * @param user_id ユーザーID
	 * @param created 生成日付
	 * @param updated 更新日時
	 */
	public LoginModel(LoginIdStatus id,
			RoomIdStatus room_id,
			UserIdStatus user_id,
			LocalDateTime created,
			LocalDateTime updated) {
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
		this.updated	= updated;
	}
	
	/**
	 * コンストラクタ
	 * @param room_id ルームID
	 * @param user_id ユーザーID
	 * @param created 生成日付
	 * @param updated 更新日時
	 */
	public LoginModel(RoomIdStatus room_id,
			UserIdStatus user_id,
			LocalDateTime created,
			LocalDateTime updated) {
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
		this.updated	= updated;
	}
	
	/**
	 * コンストラクタ
	 * @param model ログインモデルクラス
	 */
	public LoginModel(LoginModel model) {
		super();

		if( model == null ) {
			LocalDateTime now = LocalDateTime.now();
			this.id 		= new LoginIdStatus(0);
			this.room_id 	= new RoomIdStatus(0);
			this.user_id 	= new UserIdStatus(0);
			this.created 	= now;
			this.updated	= now;
		} else {
			this.id 		= new LoginIdStatus(model.getId());
			this.room_id 	= new RoomIdStatus(model.getRoom_id());
			this.user_id 	= new UserIdStatus(model.getUser_id());
			this.created 	= model.getCreated();
			this.updated	= model.getUpdated();
		}
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
	
	public LocalDateTime getUpdated() {
		return updated;
	}

	protected void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
}
