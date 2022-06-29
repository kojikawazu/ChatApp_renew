package com.example.demo.app.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ルーム生成フォーム
 *
 */
public class RoomCreateForm {
	
	// ルーム名
	@NotNull
	@Size(min = 1, max =20, message = "20文字を超えています。")
	private String name;
	
	// ルームコメント
	@NotNull
	@Size(min = 1, max =50, message = "50文字を超えています。")
	private String comment;
	
	// ルームタグ
	@NotNull
	@Size(min = 1, max =20, message = "20文字を超えています。")
	private String tag;
	
	// ルーム最大ユーザ数
	@NotNull
	@Min(2)
	@Max(10)
	private int max_roomsum;
	
	// ホスト_ログインID
	private int login_id;

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
