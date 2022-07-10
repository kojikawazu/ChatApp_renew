package com.example.demo.app.form;

/**
 * 強制退室フォーム
 * @author nanai
 *
 */
public class RoomLeaveForm implements SuperRoomForm {
	
	/** ログイン_ユーザID */
	private int in_id;
	
	/** 入室ID */
	private int enter_id;

	/**
	 * コンストラクタ
	 */
	public RoomLeaveForm() {
		super();
	}

	public int getEnter_id() {
		return enter_id;
	}

	public void setEnter_id(int enter_id) {
		this.enter_id = enter_id;
	}

	public int getIn_id() {
		return in_id;
	}

	public void setIn_id(int in_id) {
		this.in_id = in_id;
	}
}
