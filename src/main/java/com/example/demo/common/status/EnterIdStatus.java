package com.example.demo.common.status;

import com.example.demo.app.config.WebConsts;

/**
 * 入室IDのステータス受け渡しクラス
 */
public class EnterIdStatus implements SuperStatus {

	/**
	 * フィールド
	 * 
	 */
	private int enterId;		/** 入室ID */
	
	/**
	 * コンストラクタ
	 * @param enterId 入室ID
	 */
	public EnterIdStatus(int enterId) {
		this.enterId = enterId;
	}
	
	/**
	 * エラーか判定する
	 * @return True エラー False エラーでない
	 */
	@Override
	public boolean isError() {
		return ( this.enterId == WebConsts.ERROR_NUMBER );
	}

	/**
	 * IDの取得
	 * @return ID 入室ID
	 */
	@Override
	public int getId() {
		return this.enterId;
	}
}
