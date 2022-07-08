package com.example.demo.app.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ログインフォーム
 * @author nanai
 *
 */
public class UserLoginForm implements SuperUserForm {
	
	/** ユーザ名 */
	@NotNull
	@Size(min       = 0, 
			max     = USER_NAME_MAX_VALUE, 
			message = ERROR_USER_NAME_MAX_MESSAGE)
	private String name;
	
	/** Eメール */
	@NotNull
	@Email
	@Size(min       = 0, 
			max     = EMAIL_NAME_MAX_VALUE, 
			message = ERROR_EMAIL_NAME_MAX_MESSAGE)
	private String email;
	
	/** パスワード */
	@NotNull
	@Size(min       = 0, 
    		max     = PASSWD_NAME_MAX_VALUE, 
    		message = ERROR_PASSWD_NAME_MAX_MESSAGE)
	private String passwd;

	/**
	 * コンストラクタ
	 */
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
