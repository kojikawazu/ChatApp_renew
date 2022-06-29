package com.example.demo.app.entity;

/**
 * ルームモデル拡張版
 *
 */
public class RoomModelEx extends RoomModel {

	private String userName;			// ユーザ名
	
	private int enterCnt;				// 入室数

	public RoomModelEx() {
		super();
	}
	
	public RoomModelEx(RoomModel roomModel) {
		super();
		this.setId(roomModel.getId());
		this.setName(roomModel.getName());
		this.setTag(roomModel.getTag());
		this.setComment(roomModel.getComment());
		this.setMax_roomsum(roomModel.getMax_roomsum());
		this.setUser_id(roomModel.getUser_id());
		this.setCreated(roomModel.getCreated());
		this.setUpdated(roomModel.getUpdated());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getEnterCnt() {
		return enterCnt;
	}

	public void setEnterCnt(int enterCnt) {
		this.enterCnt = enterCnt;
	}
	
}
