package com.example.demo.common.word;

/**
 * ルームコメントクラス
 * @author nanai
 *
 */
public class RoomCommentWord implements SuperWord {

	/**
	 * フィールド
	 */
	private String comment;		/** コメント */

	/**
	 * コンストラクタ
	 */
	public RoomCommentWord(String comment) {
		this.comment = comment;
	}

	/**
	 * ブランクかどうか確認
	 * return true ブランク false ブランクじゃない
	 */
	@Override
	public boolean isBlank() {
		return ( this.comment == "" );
	}

	/**
	 * 文字列の取得
	 * return コメント
	 */
	@Override
	public String getString() {
		return this.comment;
	}
}
