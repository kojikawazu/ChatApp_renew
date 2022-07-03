package com.example.demo.app.entity;

import com.example.demo.common.word.NameWord;

/**
 * コメントモデル拡張版
 *
 */
public class CommentModelEx extends CommentModel {

	/**
	 * フィールド
	 * 
	 */
	private NameWord userName;			/** ユーザ名 */
	
	/**
	 * コンストラクタ
	 * @param model コメントモデルクラス
	 */
	public CommentModelEx(CommentModel model) {
		super(model);
		
		this.userName = new NameWord("");
	}
	
	/**
	 * コンストラクタ
	 * @param model コメントモデルクラス
	 * @param userName ユーザー名
	 */
	public CommentModelEx(CommentModel model, NameWord userName) {
		super(model);
		
		this.userName = (userName == null ?
				new NameWord("") :
				new NameWord(userName.getString()));
	}

	/**
	 * コンストラクタ
	 * @param model コメントモデルクラス拡張版
	 */
	public CommentModelEx(CommentModelEx model) {
		super(model);
		
		if( model == null) {
			this.userName = new NameWord("");
		} else {
			this.userName = new NameWord(model.getUserName());
		}
	}

	public String getUserName() {
		return userName.getString();
	}

	protected void setUserName(String userName) {
		this.userName = new NameWord(userName);
	}
}
