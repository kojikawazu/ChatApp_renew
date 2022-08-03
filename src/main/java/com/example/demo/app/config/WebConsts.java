package com.example.demo.app.config;

/**
 * 定数群
 * @author nanai
 *
 */
public class WebConsts {
	
	/** エラー番号 */
	public static final int ERROR_NUMBER = -1;
	
	/** チェック処理判定結果(OK) */
	public static final boolean CHECK_COMMON_OK = true;
		
	/** チェック処理判定結果(NG) */
	public static final boolean CHECK_COMMON_NG = false;
	
	/** DBエラー番号 */
	public static final int ERROR_DB_STATUS = 0;
	
	/** DB選択したがなかった */
	public static final boolean DB_ENTITY_NONE  = false;
	
	/** DB選択し見つかった */
	public static final boolean DB_ENTITY_FINDED = true;
	
	/** 時間がだいぶ経っている */
	public static final boolean TIME_RUNNING_OUT = false;
	
	/** 時間内 */
	public static final boolean TIME_WITHIN = true;
	
	/** */
	public static final int TIME_TIMER_NUMBER_HOUR = 12;
	
	
	/** ログインID初期化番号 */
	public static final int LOGIN_ID_INIT_NUMBER = 0;
	
	// ---------------------------------------------------------------------------
	
	public static final String BIND_TITLE = "title";
	
	public static final String BIND_CONT = "cont";
	
	public static final String BIND_LOGIN_ID = "login_id";
	
	public static final String BIND_ENCRYPT_LOGIN_ID = "e_loginId";
	
	public static final String BIND_ENTER_ID = "enter_id";
	
	public static final String BIND_ENCRYPT_ENTER_ID = "e_enterId";
	
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

}
