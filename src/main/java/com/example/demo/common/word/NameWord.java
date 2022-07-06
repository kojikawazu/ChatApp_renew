package com.example.demo.common.word;

/**
 * 名前クラス
 * @author nanai
 *
 */
public class NameWord implements SuperWord {

	/**
	 * フィールド
	 */
	private String name;		/** 名前 */

	/**
	 * コンストラクタ
	 */
	public NameWord(String name) {
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
