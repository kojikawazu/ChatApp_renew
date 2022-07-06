package com.example.demo.common.number;

/**
 * ルーム最大人数クラス
 * @author nanai
 *
 */
public class RoomMaxNumber implements SuperNumber {

	/**
	 * フィールド
	 */
	private int roomMax;		/** ルーム最大人数 */

	/**
	 * コンストラクタ
	 */
	public RoomMaxNumber(int roomMax) {
		this.roomMax = roomMax;
	}
	
	/**
	 * 数値の取得
	 * @return ルーム最大人数
	 */
	@Override
	public int getNumber() {
		return this.roomMax;
	}
}
