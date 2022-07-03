package com.example.demo.common.status;

import com.example.demo.app.config.WebConsts;

/**
 * ルームIDのステータス受け渡しクラス
 * @author nanai
 * 
 */
public class RoomIdStatus implements SuperStatus {

	/**
	 * フィールド
	 * 
	 */
	private int roomId;		/** ルームID */
	
	/**
	 * コンストラクタ
	 * @param roomId ルームID
	 */
	public RoomIdStatus(int roomId) {
		this.roomId = roomId;
	}
	
	/**
	 * エラーか判定する
	 * @return True エラー False エラーでない
	 */
	@Override
	public boolean isError() {
		return ( this.roomId == WebConsts.ERROR_NUMBER );
	}

	/**
	 * IDの取得
	 * @return ID ルームID
	 */
	@Override
	public int getId() {
		return this.roomId;
	}

}
