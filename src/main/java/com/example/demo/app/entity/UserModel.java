package com.example.demo.app.entity;

import java.time.LocalDateTime;

/**
 * ユーザモデル
 *
 */
public class UserModel {
	
	private int id;					// ユーザID
	private String name;				// ユーザ名
	private String email;				// Eメール
	private String passwd;				// パスワード
	private String forgot_passwd;		// 忘れたとき用パスワード
	private LocalDateTime created;		// 生成日時
	private LocalDateTime updated;		// 更新日時
	
	public UserModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getForgot_passwd() {
		return forgot_passwd;
	}

	public void setForgot_passwd(String forgot_passwd) {
		this.forgot_passwd = forgot_passwd;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
	
	
	
	
	

}
