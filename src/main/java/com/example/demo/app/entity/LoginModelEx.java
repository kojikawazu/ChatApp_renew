package com.example.demo.app.entity;

/**
 * ログインモデル拡張版
 *
 */
public class LoginModelEx extends LoginModel{

	private String userName;			// ユーザ名
	
	/**
	 * コンストラクタ
	 * @param model　LoginModelクラス
	 */
	public LoginModelEx(LoginModel model) {
		super(model);
		this.userName = "";
	}
	
	/**
	 * コンストラクタ
	 * @param model LoginModelクラス
	 * @param userName ユーザー名
	 */
	public LoginModelEx(LoginModel model, String userName) {
		super(model);
		this.userName = userName;
	}
	
	/**
	 * コンストラクタ
	 * @param model　LoginModelExクラス
	 */
	public LoginModelEx(LoginModelEx model) {
		super(model);
		if( model == null ) {
			this.userName = "";
		} else {
			this.userName = model.getUserName();
		}
	}

	public String getUserName() {
		return userName;
	}

	protected void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
