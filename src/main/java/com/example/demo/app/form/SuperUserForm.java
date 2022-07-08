package com.example.demo.app.form;

public interface SuperUserForm extends SuperForm {

	/** 名前の文字数上限数 */
	public static final int USER_NAME_MAX_VALUE = 20;
	
	/** 名前の文字数下限数 */
	public static final int USER_NAME_MIN_VALUE = 3;
	
	/** Eメールアドレスの文字数上限数 */
	public static final int EMAIL_NAME_MAX_VALUE = 20;
	
	/** Eメールアドレスの文字数下限数 */
	public static final int EMAIL_NAME_MIN_VALUE = 1;
	
	/** パスワードの文字数上限数 */
	public static final int PASSWD_NAME_MAX_VALUE = 20;
	
	/** パスワードの文字数下限数 */
	public static final int PASSWD_NAME_MIN_VALUE = 5;
	
	/** 忘れた時用のパスワードの文字数上限数 */
	public static final int FORGOT_PASSWD_NAME_MAX_VALUE = 20;
	
	/** 忘れた時用のパスワードの文字数下限数 */
	public static final int FORGOT_PASSWD_NAME_MIN_VALUE = 5;
	
	/** 名前の文字数上限(エラーメッセージ) */
	public static final String ERROR_USER_NAME_MAX_MESSAGE = 
			USER_NAME_MIN_VALUE + "文字以上" + USER_NAME_MAX_VALUE + "文字以下にしてください。";
	
	/** Eメールアドレスの文字数上限(エラーメッセージ) */
	public static final String ERROR_EMAIL_NAME_MAX_MESSAGE = 
			EMAIL_NAME_MIN_VALUE + "文字以上" + EMAIL_NAME_MAX_VALUE + "文字以下にしてください。";
	
	/** パスワードの文字数上限(エラーメッセージ) */
	public static final String ERROR_PASSWD_NAME_MAX_MESSAGE = 
			PASSWD_NAME_MIN_VALUE + "文字以上" + PASSWD_NAME_MAX_VALUE + "文字以下にしてください。";
	
	/** 忘れた時用のパスワードの文字数上限(エラーメッセージ) */
	public static final String ERROR_FORGOT_PASSWD_NAME_MAX_MESSAGE = 
			FORGOT_PASSWD_NAME_MIN_VALUE + "文字以上" + FORGOT_PASSWD_NAME_MAX_VALUE + "文字以下にしてください。";
}
