package com.example.demo.app.form;

/**
 * ログアウトフォーム
 * @author nanai
 *
 */
public class UserLogoutForm implements SuperUserForm {

	/** ユーザID */
	private int id;

	/**
	 * コンストラクタ
	 */
	public UserLogoutForm() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
