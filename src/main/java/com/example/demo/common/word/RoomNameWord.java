package com.example.demo.common.word;

/**
 * ルーム名クラス
 * @author nanai
 *
 */
public class RoomNameWord implements SuperWord {

	/**
	 * フィールド
	 */
	private String name;		/** ルーム名 */

	/**
	 * コンストラクタ
	 */
	public RoomNameWord(String name) {
		this.name = name;
	}

	/**
	 * ブランクかどうか確認
	 * @return true ブランク false ブランクじゃない
	 */
	@Override
	public boolean isBlank() {
		return ( this.name == "" );
	}

	/**
	 * 文字列の取得
	 * @return 名前
	 */
	@Override
	public String getString() {
		return this.name;
	}
}
