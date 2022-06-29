package com.example.demo.app.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * サインアップフォーム
 *
 */
public class UserSignupForm {

	// ユーザ名
	@NotNull
	@Size(min = 1, max =20, message = "20文字を超えています。")
	private String name;
	
	// Eメール
	@NotNull
	@Email
	@Size(min = 1, max =20, message = "20文字を超えています。")
	private String email;
	
	// 新しいパスワード
	@NotNull
	@Size(min = 1, max =20, message = "20文字を超えています。")
	private String new_passwd;
	
	// 忘れたとき用パスワード
	@NotNull
	@Size(min = 1, max =20, message = "20文字を超えています。")
	private String forgot_passwd;

	public UserSignupForm() {
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

	public String getNew_passwd() {
		return new_passwd;
	}

	public void setNew_passwd(String new_passwd) {
		this.new_passwd = new_passwd;
	}

	public String getForgot_passwd() {
		return forgot_passwd;
	}

	public void setForgot_passwd(String forgot_passwd) {
		this.forgot_passwd = forgot_passwd;
	}
	
	
}
