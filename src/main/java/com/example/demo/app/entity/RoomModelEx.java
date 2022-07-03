package com.example.demo.app.entity;

import com.example.demo.common.number.RoomEnterCntNumber;
import com.example.demo.common.word.NameWord;

/**
 * ルームモデル拡張版
 * @author nanai
 *
 */
public class RoomModelEx extends RoomModel {

	/**
	 * フィールド
	 * 
	 */
	private NameWord userName;				/** ユーザ名 */
	private RoomEnterCntNumber enterCnt;	/** 入室数 */

	/**
	 * コンストラクタ
	 * @param roomModel ルームモデルクラス
	 */
	public RoomModelEx(RoomModel model) {
		super(model);
		
		this.userName = new NameWord("");
		this.enterCnt = new RoomEnterCntNumber(0);
	}
	
	/**
	 * コンストラクタ
	 * @param roomModel ルームモデルクラス
	 */
	public RoomModelEx(
			RoomModel model,
			NameWord userName,
			RoomEnterCntNumber enterCnt) {
		super(model);
		
		this.userName = (userName == null ?
				new NameWord("") :
				new NameWord(userName.getString()));
		this.enterCnt = (enterCnt == null ?
				new RoomEnterCntNumber(0) :
				new RoomEnterCntNumber(enterCnt.getNumber()));
	}

	/**
	 * コンストラクタ
	 * @param roomModel ルームモデルクラス
	 */
	public RoomModelEx(
			RoomModelEx model) {
		super(model);

		if ( model == null ) {
			this.userName = new NameWord("");
			this.enterCnt = new RoomEnterCntNumber(0);
		} else {
			this.userName = new NameWord(model.getUserName());
			this.enterCnt = new RoomEnterCntNumber(model.getEnterCnt());
		}
	}
	
	public String getUserName() {
		return userName.getString();
	}

	protected void setUserName(String userName) {
		this.userName = new NameWord(userName);
	}

	public int getEnterCnt() {
		return enterCnt.getNumber();
	}

	protected void setEnterCnt(int enterCnt) {
		this.enterCnt = new RoomEnterCntNumber(enterCnt);
	}
}
