package com.example.demo.common.word;

/**
 * パスワードクラス
 * @author nanai
 *
 */
public class PasswordWord implements SuperWord {

	/**
	 * 名前フィールド
	 */
	private String password;
	
	/**
	 * コンストラクタ
	 */
	public PasswordWord(String password) {
		this.password = password;
	}
	
	/**
	 * ブランクかどうか確認
	 * return true ブランク false ブランクじゃない
	 */
	@Override
	public boolean isBlank() {
		return ( this.password == "" );
	}

	/**
	 * 文字列の取得
	 * return パスワード
	 */
	@Override
	public String getString() {
		return this.password;
	}

}
