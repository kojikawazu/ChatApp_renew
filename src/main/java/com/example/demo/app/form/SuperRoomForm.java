package com.example.demo.app.form;

/**
 * ルームインターフェース
 * @author nanai
 *
 */
public interface SuperRoomForm extends SuperForm {

	/** ルーム名の文字列下限数 */
	public static final int ROOM_NAME_MIN_VALUE = 1;
	
	/** ルーム名の文字列上限数 */
	public static final int ROOM_NAME_MAX_VALUE = 20;
	
	/** ルームコメントの文字列下限数 */
	public static final int ROOM_COMMENT_MIN_VALUE = 1;
	
	/** ルームコメントの文字列上限数 */
	public static final int ROOM_COMMENT_MAX_VALUE = 50;
	
	/** ルームタグの文字列下限数 */
	public static final int ROOM_TAG_MIN_VALUE = 1;
	
	/** ルームタグの文字列上限数 */
	public static final int ROOM_TAG_MAX_VALUE = 20;
	
	/** ルーム最少人数 */
	public static final int ROOM_ENTER_USER_MIN_VALUE = 2;
	
	/** ルーム最大人数 */
	public static final int ROOM_ENTER_USER_MAX_VALUE = 10;
	
	/** ルーム名の文字数上限(エラーメッセージ) */
	public static final String ERROR_ROOM_NAME_MAX_MESSAGE = 
			ROOM_NAME_MIN_VALUE + "文字以上" + ROOM_NAME_MAX_VALUE + "文字以下にしてください。";
	
	/** ルームコメントの文字数上限(エラーメッセージ) */
	public static final String ERROR_ROOM_COMMENT_MAX_MESSAGE = 
			ROOM_COMMENT_MIN_VALUE + "文字以上" + ROOM_COMMENT_MAX_VALUE + "文字以下にしてください。";
	
	/** ルームタグの文字数上限(エラーメッセージ) */
	public static final String ERROR_ROOM_TAG_MAX_MESSAGE = 
			ROOM_TAG_MIN_VALUE + "文字以上" + ROOM_TAG_MAX_VALUE + "文字以下にしてください。";
	
}
