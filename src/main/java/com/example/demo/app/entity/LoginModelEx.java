package com.example.demo.app.entity;

import com.example.demo.common.word.NameWord;

/**
 * ログインモデル拡張版
 * @author nanai
 *
 */
public class LoginModelEx extends LoginModel {

	/**
	 * フィールド
	 * 
	 */
	private NameWord userName;			/** ユーザ名 */
	
	/**
	 * コンストラクタ
	 * @param model LoginModelクラス
	 * @param userName ユーザー名
	 */
	public LoginModelEx(LoginModel model, NameWord userName) {
		super(model);
		
		this.userName = ( userName == null ?
				new NameWord("") :
				new NameWord(userName.getString()));
	}
	
	/**
	 * コンストラクタ
	 * @param model LoginModelクラス
	 */
	public LoginModelEx(LoginModel model) {
		super(model);
		this.userName = new NameWord("");
	}
	
	/**
	 * コンストラクタ
	 * @param model LoginModelExクラス
	 */
	public LoginModelEx(LoginModelEx model) {
		super(model);
		if( model == null ) {
			this.userName =  new NameWord("");
		} else {
			this.userName =  new NameWord(model.getUserName());
		}
	}

	public String getUserName() {
		return userName.getString();
	}

	protected void setUserName(String userName) {
		this.userName = new NameWord(userName);
	}
}
