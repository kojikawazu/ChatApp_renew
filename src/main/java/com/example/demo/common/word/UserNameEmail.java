package com.example.demo.common.word;

/**
 * 名前、Eメールアドレス、クラス
 * @author nanai
 *
 */
public class UserNameEmail implements SuperUserInterface {

	/**
	 * フィールド
	 */
	private NameWord 		name;			/** 名前		*/
	private EmailWord 		email;			/** Eメール	*/

	/**
	 * コンストラクタ
	 * @param name 名前
	 * @param email Eメールアドレス
	 * @param password パスワード
	 */
	public UserNameEmail(NameWord name, EmailWord email) {
		this.name 		= name;
		this.email 		= email;
	}

	/**
	 * コンストラクタ
	 * @param name 名前
	 * @param email Eメールアドレス
	 * @param password パスワード
	 */
	public UserNameEmail(String name, String email) {
		this.name 		= new NameWord(name);
		this.email 		= new EmailWord(email);
	}

	/**
	 * 名前の取得
	 * return 名前
	 */
	@Override
	public String getName() {
		return name.getString();
	}

	/**
	 * Eメールアドレスの取得
	 * return Eメールアドレス
	 */
	@Override
	public String getEmail() {
		return email.getString();
	}

	/**
	 * パスワードの取得
	 * return パスワード
	 */
	@Override
	public String getPassword() {
		return "";
	}
}
