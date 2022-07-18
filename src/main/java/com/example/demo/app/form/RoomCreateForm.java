package com.example.demo.app.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ルーム生成フォーム
 * @author nanai
 *
 */
public class RoomCreateForm implements SuperRoomForm {
	
	/** ルーム名 */
	@NotNull(message = ERROR_NULL_STRING_MESSAGE)
	@Size(min = ROOM_NAME_MIN_VALUE, 
			max = ROOM_NAME_MAX_VALUE, 
			message = ERROR_ROOM_NAME_MAX_MESSAGE)
	private String name;
	
	/** ルームコメント */
	@NotNull(message = ERROR_NULL_STRING_MESSAGE)
	@Size(min = ROOM_COMMENT_MIN_VALUE, 
			max = ROOM_COMMENT_MAX_VALUE, 
			message = ERROR_ROOM_COMMENT_MAX_MESSAGE)
	private String comment;
	
	/** ルームタグ */
	@NotNull(message = ERROR_NULL_STRING_MESSAGE)
	@Size(min = ROOM_TAG_MIN_VALUE, 
			max = ROOM_TAG_MAX_VALUE, 
			message = ERROR_ROOM_TAG_MAX_MESSAGE)
	private String tag;
	
	/** ルーム最大ユーザ数 */
	@NotNull
	@Min(ROOM_ENTER_USER_MIN_VALUE)
	@Max(ROOM_ENTER_USER_MAX_VALUE)
	private int max_roomsum;
	
	/** ホスト_ログインID */
	private int login_id;

	/**
	 * コンストラクタ
	 */
	public RoomCreateForm() {
		super();
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

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
}
