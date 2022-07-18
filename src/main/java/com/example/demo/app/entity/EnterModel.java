package com.example.demo.app.entity;

import java.time.LocalDateTime;

import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 入室モデル
 * @author nanai
 *
 */
public class EnterModel implements SuperModel {
	
	/**
	 * フィールド
	 * 
	 */
	private EnterIdStatus id;			/** 入室ID */
	private RoomIdStatus room_id;		/** ルームID */
	private UserIdStatus user_id;		/** ユーザID */
	private UserIdStatus manager_id;	/** ホストID */
	private LocalDateTime created;		/** 生成日時 */
	
	/**
	 * コンストラクタ
	 * @param id         入室ID
	 * @param room_id    ルームID
	 * @param user_id    ユーザID
	 * @param manager_id ホストID
	 * @param max_sum    入室数
	 * @param created    生成日時
	 */
	public EnterModel(
			EnterIdStatus id,
			RoomIdStatus room_id,
			UserIdStatus user_id,
			UserIdStatus manager_id,
			LocalDateTime created) {
		
		this.id = (id == null ?
				new EnterIdStatus(0) :
				new EnterIdStatus(id.getId()));
		
		this.room_id = (room_id == null ?
				new RoomIdStatus(0) :
				new RoomIdStatus(room_id.getId()));
		
		this.user_id = (user_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(user_id.getId()));
		
		this.manager_id = (manager_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(manager_id.getId()));
		
		this.created = (created == null ?
				LocalDateTime.now() :
				created);
	}
	
	/**
	 * コンストラクタ
	 * @param room_id    ルームID
	 * @param user_id    ユーザID
	 * @param manager_id ホストID
	 * @param max_sum    入室数
	 * @param created    生成日時
	 */
	public EnterModel(
			RoomIdStatus room_id,
			UserIdStatus user_id,
			UserIdStatus manager_id,
			LocalDateTime created) {
		this.id = new EnterIdStatus(0);
		
		this.room_id = (room_id == null ?
				new RoomIdStatus(0) :
				new RoomIdStatus(room_id.getId()));
		
		this.user_id = (user_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(user_id.getId()));
		
		this.manager_id = (manager_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(manager_id.getId()));
		
		this.created = (created == null ?
				LocalDateTime.now() :
				created);
	}
	
	/**
	 * コンストラクタ
	 * @param model 入室モデル
	 */
	public EnterModel(EnterModel model) {
		
		if( model == null ) {
			this.id 		= new EnterIdStatus(0);
			this.room_id 	= new RoomIdStatus(0);
			this.user_id 	= new UserIdStatus(0);
			this.manager_id = new UserIdStatus(0);
			this.created 	= LocalDateTime.now();
		} else {
			this.id 		= new EnterIdStatus(model.getId());
			this.room_id 	= new RoomIdStatus(model.getRoom_id());
			this.user_id 	= new UserIdStatus(model.getUser_id());
			this.manager_id = new UserIdStatus(model.getManager_id());
			this.created 	= model.created;
		}
	}

	public int getId() {
		return id.getId();
	}
	
	protected void setId(int id) {
		this.id = new EnterIdStatus(id);
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

	public int getManager_id() {
		return manager_id.getId();
	}

	protected void setManager_id(int manager_id) {
		this.manager_id = new UserIdStatus(manager_id);
	}

	public LocalDateTime getCreated() {
		return created;
	}

	protected void setCreated(LocalDateTime created) {
		this.created = created;
	}
}
