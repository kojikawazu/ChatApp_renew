package com.example.demo.common.word;

/**
 * 名前、Eメールアドレス、パスワードクラス
 * @author nanai
 *
 */
public class UserNameEmailPassword implements SuperUserInterface{

	/**
	 * フィールド
	 */
	private NameWord 		name;			/** 名前		*/
	private EmailWord 		email;			/** Eメール	*/
	private PasswordWord 	password;		/** パスワード */

	/**
	 * コンストラクタ
	 * @param name 名前
	 * @param email Eメールアドレス
	 * @param password パスワード
	 */
	public UserNameEmailPassword(NameWord name, EmailWord email, PasswordWord password) {
		this.name 		= name;
		this.email 		= email;
		this.password 	= password;
	}

	/**
	 * コンストラクタ
	 * @param name 名前
	 * @param email Eメールアドレス
	 * @param password パスワード
	 */
	public UserNameEmailPassword(String name, String email, String password) {
		this.name 		= new NameWord(name);
		this.email 		= new EmailWord(email);
		this.password 	= new PasswordWord(password);
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
		return password.getString();
	}
}
