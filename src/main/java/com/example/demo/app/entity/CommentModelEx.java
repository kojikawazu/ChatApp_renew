package com.example.demo.app.entity;

/**
 * コメントモデル拡張版
 *
 */
public class CommentModelEx extends CommentModel{
	
	private String userName;			// ユーザ名
	
	public CommentModelEx() {
		super();
	}
	
	public CommentModelEx(CommentModel model) {
		super();
		this.setId(model.getId());
		this.setUser_id(model.getUser_id());
		this.setRoom_id(model.getRoom_id());
		this.setComment(model.getComment());
		this.setCreated(model.getCreated());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
