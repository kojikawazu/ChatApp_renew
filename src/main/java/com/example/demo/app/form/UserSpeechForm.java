package com.example.demo.app.form;

/**
 * コメントフォーム
 * @author nanai
 */
public class UserSpeechForm implements SuperUserForm {
	
	/** コメントID */
	private int enter_id;
	
	/** ルームID */
	private int room_id;
	
	/** ユーザID */
	private int user_id;
	
	/** コメント */
	private String comment;

	/**
	 * コンストラクタ
	 */
	public UserSpeechForm() {
		super();
	}

	public int getEnter_id() {
		return enter_id;
	}

	public void setEnter_id(int enter_id) {
		this.enter_id = enter_id;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
