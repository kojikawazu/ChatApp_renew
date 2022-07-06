package com.example.demo.common.word;

/**
 * チャットコメントクラス
 * @author nanai
 *
 */
public class ChatCommentWord implements SuperWord {

	/**
	 * フィールド
	 */
	private String comment;		/** コメント */

	/**
	 * コンストラクタ
	 */
	public ChatCommentWord(String comment) {
		this.comment = comment;
	}

	/**
	 * ブランクかどうか確認
	 * @return true ブランク false ブランクじゃない
	 */
	@Override
	public boolean isBlank() {
		return ( this.comment == "" );
	}

	/**
	 * 文字列の取得
	 * @return コメント
	 */
	@Override
	public String getString() {
		return this.comment;
	}
}
