package com.example.demo.app.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * サインアップフォーム
 * @author nanai
 *
 */
public class UserSignupForm implements SuperUserForm {

	/** ユーザ名 */
	@NotNull
	@Size(min       = USER_NAME_MIN_VALUE, 
			max     = USER_NAME_MAX_VALUE, 
			message = ERROR_USER_NAME_MAX_MESSAGE)
	private String name;
	
	/** Eメール */
	@NotNull
	@Email
	@Size(min       = EMAIL_NAME_MIN_VALUE, 
			max     = EMAIL_NAME_MAX_VALUE, 
			message = ERROR_EMAIL_NAME_MAX_MESSAGE)
	private String email;
	
	/** 新しいパスワード */
	@NotNull
	@Size(min           = PASSWD_NAME_MIN_VALUE, 
			max         = PASSWD_NAME_MAX_VALUE, 
			message     = ERROR_PASSWD_NAME_MAX_MESSAGE)
	@Pattern(regexp     = MATCH_SMALL_BIG_NUMBER,
				message = ERROR_MATCH_SMALL_BIG_NUMBER_MESSAGE)
	private String new_passwd;
	
	/** 忘れたとき用パスワード */
	@NotNull
	@Size(min           = FORGOT_PASSWD_NAME_MIN_VALUE, 
			max         = FORGOT_PASSWD_NAME_MAX_VALUE, 
			message     = ERROR_FORGOT_PASSWD_NAME_MAX_MESSAGE)
	@Pattern(regexp     = MATCH_SMALL_BIG_NUMBER,
				message = ERROR_MATCH_SMALL_BIG_NUMBER_MESSAGE)
	private String forgot_passwd;

	/**
	 * コンストラクタ
	 */
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
