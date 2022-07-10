package com.example.demo.app.entity;

import java.time.LocalDateTime;

import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.RoomCommentWord;
import com.example.demo.common.word.RoomNameWord;
import com.example.demo.common.word.RoomTagWord;

/**
 * ルームモデル
 * @author nanai
 *
 */
public class RoomModel implements SuperModel {
	
	/**
	 * フィールド
	 * 
	 */
	private RoomIdStatus id;			/** ルームID    */
	private RoomNameWord name;			/** ルーム名    */
	private RoomCommentWord comment;	/** コメント     */
	private RoomTagWord tag;			/** タグ       */
	private RoomMaxNumber max_roomsum;	/** 入室最大数 */
	private UserIdStatus user_id;		/** ホストID    */
	private LocalDateTime created;		/** 生成日時   */
	private LocalDateTime updated;		/** 更新日時   */
	
	/**
	 * コンストラクタ
	 * @param id          ID
	 * @param name        ルーム名
	 * @param comment     ルームコメント
	 * @param tag         ルームタグ
	 * @param max_roomsum ルーム最大人数
	 * @param user_id     ホストID
	 * @param created     生成日時
	 * @param updated     更新日時
	 */
	public RoomModel(
			RoomIdStatus id,
			RoomNameWord name,
			RoomCommentWord comment,
			RoomTagWord tag,
			RoomMaxNumber max_roomsum,
			UserIdStatus user_id,
			LocalDateTime created,
			LocalDateTime updated) {
		super();
		
		this.id = (id == null ? 
				new RoomIdStatus(0) : 
				new RoomIdStatus(id.getId()));
		
		this.name = (name == null ? 
				new RoomNameWord("") : 
				new RoomNameWord(name.getString()));
		
		this.comment = (comment == null ? 
				new RoomCommentWord("") : 
				new RoomCommentWord(comment.getString()));
		
		this.tag = (tag == null ? 
				new RoomTagWord("") : 
				new RoomTagWord(tag.getString()));
		
		this.max_roomsum = (max_roomsum == null ? 
				new RoomMaxNumber(0) : 
				new RoomMaxNumber(max_roomsum.getNumber()));
		
		this.user_id = (max_roomsum == null ? 
				new UserIdStatus(0) : 
				new UserIdStatus(user_id.getId()));
		
		this.created = (created == null ?
				LocalDateTime.now() :
				created);
		
		this.updated = (updated == null ?
				LocalDateTime.now() :
				updated);
	}
	
	/**
	 * コンストラクタ
	 * @param name        ルーム名
	 * @param comment     ルームコメント
	 * @param tag         ルームタグ
	 * @param max_roomsum ルーム最大人数
	 * @param user_id     ホストID
	 * @param created     生成日時
	 * @param updated     更新日時
	 */
	public RoomModel(
			RoomNameWord name,
			RoomCommentWord comment,
			RoomTagWord tag,
			RoomMaxNumber max_roomsum,
			UserIdStatus user_id,
			LocalDateTime created,
			LocalDateTime updated) {
		super();
		this.id = new RoomIdStatus(0);
		
		this.name = (name == null ? 
				new RoomNameWord("") : 
				new RoomNameWord(name.getString()));
		
		this.comment = (comment == null ? 
				new RoomCommentWord("") : 
				new RoomCommentWord(comment.getString()));
		
		this.tag = (tag == null ? 
				new RoomTagWord("") : 
				new RoomTagWord(tag.getString()));
		
		this.max_roomsum = (max_roomsum == null ? 
				new RoomMaxNumber(0) : 
				new RoomMaxNumber(max_roomsum.getNumber()));
		
		this.user_id = (max_roomsum == null ? 
				new UserIdStatus(0) : 
				new UserIdStatus(user_id.getId()));
		
		this.created = (created == null ?
				LocalDateTime.now() :
				created);
		
		this.updated = (updated == null ?
				LocalDateTime.now() :
				updated);
	}
	
	/**
	 * コンストラクタ
	 * @param model ルームモデルクラス
	 */
	public RoomModel(RoomModel model) {
		super();

		if( model == null ) {
			this.id 				= new RoomIdStatus(0);
			this.name 				= new RoomNameWord("");
			this.comment 			= new RoomCommentWord("");
			this.tag 				= new RoomTagWord("");
			this.max_roomsum 		= new RoomMaxNumber(0);
			this.user_id			= new UserIdStatus(0);
			this.created 			= LocalDateTime.now();
			this.updated 			= LocalDateTime.now();
		} else {
			this.id 				= new RoomIdStatus(model.getId());
			this.name 				= new RoomNameWord(model.getName());
			this.comment 			= new RoomCommentWord(model.getComment());
			this.tag 				= new RoomTagWord(model.getTag());
			this.max_roomsum 		= new RoomMaxNumber(model.getMax_roomsum());
			this.user_id			= new UserIdStatus(model.getUser_id());
			this.created 			= model.getCreated();
			this.updated 			= model.getUpdated();
		}
	}

	public int getId() {
		return id.getId();
	}

	protected void setId(int id) {
		this.id = new RoomIdStatus(id);
	}

	public String getName() {
		return name.getString();
	}

	protected void setName(String name) {
		this.name = new RoomNameWord(name);
	}

	public String getComment() {
		return comment.getString();
	}

	protected void setComment(String comment) {
		this.comment = new RoomCommentWord(comment);
	}

	public String getTag() {
		return tag.getString();
	}

	protected void setTag(String tag) {
		this.tag = new RoomTagWord(tag);
	}

	public int getMax_roomsum() {
		return max_roomsum.getNumber();
	}

	protected void setMax_roomsum(int max_roomsum) {
		this.max_roomsum = new RoomMaxNumber(max_roomsum);
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
