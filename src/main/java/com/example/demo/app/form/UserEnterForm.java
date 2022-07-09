package com.example.demo.app.form;

/**
 * 入室フォーム
 * @author nanai
 *
 */
public class UserEnterForm {
	
	/** ルームID */
	private int room_id;
	
	/** ログインID */
	private int login_id;
	
	/** ログインユーザ数 */
	private int count_sum;
	
	/** ログイン最大数 */
	private int max_sum;

	/**
	 * コンストラクタ
	 */
	public UserEnterForm() {
		super();
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}

	public int getCount_sum() {
		return count_sum;
	}

	public void setCount_sum(int count_sum) {
		this.count_sum = count_sum;
	}

	public int getMax_sum() {
		return max_sum;
	}

	public void setMax_sum(int max_sum) {
		this.max_sum = max_sum;
	}
}
