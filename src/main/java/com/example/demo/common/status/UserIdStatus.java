package com.example.demo.common.status;

import com.example.demo.app.config.WebConsts;

/**
 * ユーザーIDのステータス受け渡しクラス
 */
public class UserIdStatus implements SuperStatus {

	/**
	 * フィールド
	 * 
	 */
	private int userId;		/** ユーザーID */
	
	/**
	 * コンストラクタ
	 * @param userId ユーザーID
	 */
	public UserIdStatus(int userId) {
		this.userId = userId;
	}
	
	/**
	 * エラーか判定する
	 * @return True エラー False エラーでない
	 */
	@Override
	public boolean isError() {
		return ( this.userId == WebConsts.ERROR_NUMBER );
	}
	
	/**
	 * IDの取得
	 * @return ID ユーザーID
	 */
	@Override
	public int getId() {
		return this.userId;
	}
}
