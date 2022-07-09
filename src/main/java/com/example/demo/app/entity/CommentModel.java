package com.example.demo.app.entity;

import java.time.LocalDateTime;

import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;

/**
 * コメントモデル
 * @author nanai
 *
 */
public class CommentModel {
	
	/**
	 * フィールド
	 * 
	 */
	private CommentIdStatus id;			/** コメントID */
	private ChatCommentWord comment;	/** コメント   */
	private RoomIdStatus room_id;		/** ルームID */
	private UserIdStatus user_id;		/** ユーザID */
	private LocalDateTime created;		/** 生成日時 */
	
	/**
	 * コンストラクタ
	 * @param id コメントID
	 * @param comment コメント
	 * @param room_id ルームID
	 * @param user_id ユーザID
	 * @param created 生成日時
	 */
	public CommentModel(
			CommentIdStatus id,
			ChatCommentWord comment,
			RoomIdStatus room_id,
			UserIdStatus user_id,
			LocalDateTime created) {
		
		this.id = (id == null ?
				new CommentIdStatus(0) :
				new CommentIdStatus(id.getId()));
		
		this.comment = (comment == null ?
				new ChatCommentWord("") :
				new ChatCommentWord(comment.getString()));
		
		this.room_id = (room_id == null ?
				new RoomIdStatus(0) :
				new RoomIdStatus(room_id.getId()));
		
		this.user_id = (user_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(user_id.getId()));
		
		this.created = (created == null ?
				LocalDateTime.now() :
				created);
	}
	
	/**
	 * コンストラクタ
	 * @param comment コメント
	 * @param room_id ルームID
	 * @param user_id ユーザID
	 * @param created 生成日時
	 */
	public CommentModel(
			ChatCommentWord comment,
			RoomIdStatus room_id,
			UserIdStatus user_id,
			LocalDateTime created) {
		this.id = new CommentIdStatus(0);
		
		this.comment = (comment == null ?
				new ChatCommentWord("") :
				new ChatCommentWord(comment.getString()));
		
		this.room_id = (room_id == null ?
				new RoomIdStatus(0) :
				new RoomIdStatus(room_id.getId()));
		
		this.user_id = (user_id == null ?
				new UserIdStatus(0) :
				new UserIdStatus(user_id.getId()));
		
		this.created = (created == null ?
				LocalDateTime.now() :
				created);
	}
	
	/**
	 * コンストラクタ
	 * @param model
	 */
	public CommentModel(CommentModel model) {
		if( model == null ) {
			this.id = new CommentIdStatus(0);
			this.comment = new ChatCommentWord("");
			this.room_id = new RoomIdStatus(0);
			this.user_id = new UserIdStatus(0);
			this.created = LocalDateTime.now();
		} else {
			this.id = new CommentIdStatus(model.getId());
			this.comment = new ChatCommentWord(model.getComment());
			this.room_id = new RoomIdStatus(model.getRoom_id());
			this.user_id = new UserIdStatus(model.getUser_id());
			this.created = model.getCreated();;
		}
	}
	
	public int getId() {
		return id.getId();
	}

	protected void setId(int id) {
		this.id = new CommentIdStatus(id);
	}

	public String getComment() {
		return comment.getString();
	}

	protected void setComment(String comment) {
		this.comment = new ChatCommentWord(comment);
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
