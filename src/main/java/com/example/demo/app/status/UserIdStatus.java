package com.example.demo.app.status;

import com.example.demo.app.config.WebConsts;

/**
 * ユーザーIDのステータス受け渡しクラス
 */
public class UserIdStatus implements SuperStatus {

	private int userId;		// ユーザーID
	
	/**
	 * コンストラクタ
	 * @param userId
	 */
	public UserIdStatus(int userId) {
		this.userId = userId;
	}
	
	/**
	 * エラーか判定する
	 * @return True:エラー False: エラーでない
	 */
	@Override
	public boolean isError() {
		return ( this.userId == WebConsts.ERROR_NUMBER );
	}
	
	/**
	 * IDの取得
	 * @return ID
	 */
	@Override
	public int getId() {
		return this.userId;
	}
}
