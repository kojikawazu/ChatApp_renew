package com.example.demo.common.word;

/**
 * メールアドレスクラス
 * @author nanai
 *
 */
public class EmailWord implements SuperWord {

	/**
	 * 名前フィールド
	 */
	private String email;
	
	/**
	 * コンストラクタ
	 */
	public EmailWord(String email) {
		this.email = email;
	}
	
	/**
	 * ブランクかどうか確認
	 * return true ブランク false ブランクじゃない
	 */
	@Override
	public boolean isBlank() {
		return ( this.email == "" );
	}

	/**
	 * 文字列の取得
	 * return メールアドレス
	 */
	@Override
	public String getString() {
		return this.email;
	}

}
