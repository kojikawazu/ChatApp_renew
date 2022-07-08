package com.example.demo.app.config;

public class WebConsts {
	
	public static final int ERROR_NUMBER = -1;								// 	エラー番号
	
	// ---------------------------------------------------------------------------
	
	public static final String BIND_TITLE = "title";
	
	public static final String BIND_CONT = "cont";
	
	public static final String BIND_LOGIN_ID = "login_id";
	
	public static final String BIND_ENTER_ID = "enter_id";
	
	public static final String BIND_USER_MODEL = "userModel";
	
	public static final String BIND_NOTICE_SUCCESS = "noticeSuccess";
	
	public static final String BIND_NOTICE_ERROR = "noticeError";

	public static final String BIND_HOSTMODEL = "hostModel";
	
	public static final String BIND_USERMODEL = "userModel";
	
	public static final String BIND_ROOMMODEL = "roomModel";
	
	public static final String BIND_USERMODEL_LIST = "userModelList";

	public static final String BIND_ROOMMODEL_LIST = "roomModelList";
	
	public static final String BIND_LOGINMODEL_LIST = "loginModelList";
	
	public static final String BIND_ENTERMODEL_LIST = "enterModelList";
	
	public static final String BIND_COMMENTMODEL_LIST = "commentModelList";
	
	// ---------------------------------------------------------------------------
	
	public static final String URL_MANAGER_INDEX = "manager/index"; 
	
	public static final String URL_ROOM_INDEX = "room/index";
	
	public static final String URL_CHAT_INDEX = "chat/index";
	
	public static final String URL_USER_FORGOT_FORM = "user/forgot_form";
	
	public static final String URL_USER_FORGOT_CONFIRM = "user/forgot_confirm";
	
	public static final String URL_USER_SIGNUP_FORM = "user/signup_form";
	
	public static final String URL_USER_SIGNUP_CONFIRM = "user/signup_confirm";
	
	public static final String URL_ROOM_CREATE_FORM = "room/create_room";
	
	public static final String URL_REDIRECT_ROOM_INDEX = "redirect:/room";
	
	public static final String URL_REDIRECT_CHAT_INDEX = "redirect:/chat";
	
	// ---------------------------------------------------------------------------
	
	/** チェック処理判定結果(OK) */
	public static final boolean CHECK_COMMON_OK = true;
	
	/** チェック処理判定結果(NG) */
	public static final boolean CHECK_COMMON_NG = false;
	
	// ---------------------------------------------------------------------------
	
	public static final String ERROR_FORGOTPASSWD_NAMEEMAIL = "登録している名前とメールアドレスがありません。";
	
	public static final String ERROR_FORGOTPASSWD = "忘れた時のパスワードが違います。";
	
	// ---------------------------------------------------------------------------
	
	/** サインアップフォームタイトル */
	public static String SIGNUP_FORM_TITTLE = "サインアップ";
	
	/** サインアップフォームメッセージ */
	public static String SIGNUP_FORM_MESSAGE = "各項目を入力してください。";
	
	/** サインアップ確認画面タイトル */
	public static String SIGNUP_CONFIRM_TITTLE = "サインアップ確認";
	
	/** サインアップ確認画面メッセージ */
	public static String SIGNUP_CONFIRM_MESSAGE = "このユーザーでよろしいでしょうか？";
	
	/** パスワード変更フォーム画面タイトル */
	public static String FORGOT_PASSWORD_FORM_TITTLE = "パスワード変更";
	
	/** パスワード変更フォーム画面メッセージ */
	public static String FORGOT_PASSWORD_FORM_MESSAGE = "各項目を入力してください。";
	
	/** パスワード変更確認画面タイトル */
	public static String FORGOT_PASSWORD_CONFIRM_TITTLE = "パスワード変更確認";
	
	/** パスワード変更確認画面メッセージ */
	public static String FORGOT_PASSWORD_CONFIRM_MESSAGE = "これでよろしいですか？";
	
	/** パスワード変更実行メッセージ */
	public static String FORGOT_PASSWORD_ACTION_MESSAGE = "パスワードを変更しました。";
	
}
