package com.example.demo.common.number;

/**
 * ルーム入室人数クラス
 * @author nanai
 *
 */
public class RoomEnterCntNumber implements SuperNumber {

	/**
	 * フィールド
	 */
	private int enterCnt;		/** ルーム入室人数 */

	/**
	 * コンストラクタ
	 */
	public RoomEnterCntNumber(int enterCnt) {
		this.enterCnt = enterCnt;
	}
	
	/**
	 * 数値の取得
	 * @return ルーム入室人数
	 */
	@Override
	public int getNumber() {
		return this.enterCnt;
	}
}
