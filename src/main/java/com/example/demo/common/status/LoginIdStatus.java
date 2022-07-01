package com.example.demo.common.status;

import com.example.demo.app.config.WebConsts;

/**
 * ログインIDのステータス受け渡しクラス
 */
public class LoginIdStatus implements SuperStatus {

	private int loginId;		// ログインID
	
	/**
	 * コンストラクタ
	 * @param loginId ログインID
	 */
	public LoginIdStatus(int loginId) {
		this.loginId = loginId;
	}
	
	/**
	 * エラーか判定する
	 * @return True エラー False エラーでない
	 */
	@Override
	public boolean isError() {
		return ( this.loginId == WebConsts.ERROR_NUMBER );
	}

	/**
	 * IDの取得
	 * @return ID ログインID
	 */
	@Override
	public int getId() {
		return this.loginId;
	}

}
