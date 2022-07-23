package com.example.demo.app.user;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.form.UserLoginForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.UserService;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ---------------------------------------------------------------------------
 * 【サインイン実行コントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signin")
public class SigninController implements SuperUserController {

	/**
	 * サービス
	 */
	private UserService userService;
	private LoginService loginService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/**
	 * メッセージ
	 */
	/** ログインに失敗した時のメッセージ */
	private static final String SIGNIN_MESSAGE_ERROR_LOGIN = "ログインに失敗しました。";
	
	/** 既にログイン中のメッセージ */
	private static final String SIGNIN_MESSAGE_ERROR_ALREADY_LOGIN = "既にログインされています。";
	
	/**
	 * コンストラクタ
	 * @param userService  ユーザーサービス
	 * @param loginService ログインサービス
	 */
	@Autowired
	public SigninController(
			UserService userService,
			LoginService loginService) {
		this.userService  = userService;
		this.loginService = loginService;
	}
	
	/**
	 * サインイン受信
	 * @param userLoginForm      サインインフォーム
	 * @param result             結果
	 * @param redirectAttributes リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping
	public String index(
			@Validated UserLoginForm userLoginForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("サインイン受信...");
		
		try {
			UserIdStatus userIdStatus = new UserIdStatus(0);
			LoginIdStatus loginIdStatus = new LoginIdStatus(0);
			
			// エラーチェック
			userIdStatus = this.checkSignin(
					userLoginForm, 
					result, 
					redirectAttributes);
			if( userIdStatus.isError() ) {
				// [ERROR]
				// エラーの場合何もしない
				this.appLogger.error("ルーム画面へ");
				return WebConsts.URL_REDIRECT_ROOM_INDEX;
			}
			
			// サインイン情報登録
			loginIdStatus = this.addSignin(userIdStatus);
			if( loginIdStatus.isError() ) {
				// [ERROR]
				// エラーの場合何もしない
				this.appLogger.error("ルーム画面へ");
				return WebConsts.URL_REDIRECT_ROOM_INDEX;
			}
			
			// ログインIDをWebに登録
			String encryptNumber = CommonEncript.encrypt(loginIdStatus.getId());
			redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_LOGIN_ID, encryptNumber);
			
			this.appLogger.successed("サインイン成功 : userId : "  + userIdStatus.getId());
			this.appLogger.successed("             loginId : " + loginIdStatus.getId());
		} catch(Exception ex) {
			this.appLogger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
		// room画面表示
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * サインインチェック
	 * @param userLoginForm      ログインフォーム
	 * @param result             結果
	 * @param redirectAttributes リダイレクト
	 * @return                   ログインIDクラス
	 */
	private UserIdStatus checkSignin(
			UserLoginForm userLoginForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("サインインチェック...");
		
		if( result.hasErrors() ) {
			// エラーあり
			this.appLogger.error("バリデーションエラー: " + result);
			redirectAttributes.addFlashAttribute(
					WebConsts.BIND_NOTICE_ERROR, 
					SIGNIN_MESSAGE_ERROR_LOGIN);
			
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		// エラーなし
		
		UserIdStatus userIdStatus = this.userService.selectId_byNameEmailPasswd(
			new UserNameEmailPassword(
				userLoginForm.getName(), 
				userLoginForm.getEmail(), 
				userLoginForm.getPasswd())
			);
		
		// サインイン内容チェック
		if( userIdStatus.isError() ) {
			// [ERROR]
			this.appLogger.error("ユーザ名、Eメール、パスワード一致しない");
			redirectAttributes.addFlashAttribute(
					WebConsts.BIND_NOTICE_ERROR, 
					SIGNIN_MESSAGE_ERROR_LOGIN);
			
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		// ユーザ名、Eメール、パスワード一致
		
		// ログイン情報チェック
		if( this.loginService.isSelect_byUserId(userIdStatus) ) {
			// [ERROR]
			this.appLogger.error("ログイン情報が既に存在する。 : userId: " + userIdStatus.getId());
			redirectAttributes.addFlashAttribute(
					WebConsts.BIND_NOTICE_ERROR, 
					SIGNIN_MESSAGE_ERROR_ALREADY_LOGIN);
			
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		
		this.appLogger.successed("サインインチェックOK");
		return userIdStatus;
	}
	
	/**
	 * サインイン情報登録処理
	 * @param user_id ユーザID
	 * @return        ログインIDクラス
	 */
	private LoginIdStatus addSignin(UserIdStatus userStatus) {
		this.appLogger.start("サインイン登録...");
		
		LoginModel loginModel = new LoginModel(
				 new RoomIdStatus(0),
				 userStatus,
				 LocalDateTime.now());
		LoginIdStatus loginIdStatus = this.loginService.save_returnId(loginModel);
		
		this.appLogger.successed("サインイン登録成功: loginId: " + loginIdStatus.getId());
		return loginIdStatus;
	}
}
