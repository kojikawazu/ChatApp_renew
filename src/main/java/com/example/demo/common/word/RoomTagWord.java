package com.example.demo.common.word;

/**
 * ルームタグクラス
 * @author nanai
 *
 */
public class RoomTagWord implements SuperWord {

	/**
	 * フィールド
	 */
	private String tag;		/** タグ */

	/**
	 * コンストラクタ
	 */
	public RoomTagWord(String tag) {
		this.tag = tag;
	}

	/**
	 * ブランクかどうか確認
	 * return true ブランク false ブランクじゃない
	 */
	@Override
	public boolean isBlank() {
		return ( this.tag == "" );
	}

	/**
	 * 文字列の取得
	 * return タグ
	 */
	@Override
	public String getString() {
		return this.tag;
	}
}
