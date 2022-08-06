package com.example.demo.app.form;

/**
 * 部屋閉鎖フォーム
 * @author nanai
 *
 */
public class RoomOutForm implements SuperRoomForm {

	/** 入室ID */
	private int enter_id;
	
	/** ログインID */
	private int login_id;

	/**
	 * コンストラクタ
	 */
	public RoomOutForm() {
		super();
	}

	public int getEnter_id() {
		return enter_id;
	}

	public void setEnter_id(int enter_id) {
		this.enter_id = enter_id;
	}
	
	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
}
