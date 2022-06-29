package com.example.demo.app.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * ログインフォーム
 *
 */
public class UserLoginForm {
	
	// ユーザ名
	@NotNull
	private String name;
	
	// Eメール
	@NotNull
	@Email
	private String email;
	
	// パスワード
	@NotNull
	private String passwd;

	public UserLoginForm() {
		super();
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
	
	
	

}
