package com.example.demo.app.form;

/**
 * ルームユーザフォーム
* @author nanai
 *
 */
public class RoomUserForm {
	
	/** ログインID */
	private int login_id;

	/**
	 * コンストラクタ
	 */
	public RoomUserForm() {
		super();
	}

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
}
