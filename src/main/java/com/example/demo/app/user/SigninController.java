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
import com.example.demo.app.config.WebFunctions;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.form.UserLoginForm;
import com.example.demo.app.service.LoginService;
import com.example.demo.app.service.UserService;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ---------------------------------------------------------------------------
 * 【サインイン実行コントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signin")
public class SigninController {

	/**
	 * サービス
	 */
	private UserService userService;
	private LoginService loginService;
	
	/**
	 * メッセージ
	 */
	/** ログインに失敗した時のメッセージ */
	private static final String SIGNIN_MESSAGE_ERROR_LOGIN = "ログインに失敗しました。";
	
	/** 既にログイン中のメッセージ */
	private static final String SIGNIN_MESSAGE_ERROR_ALREADY_LOGIN = "既にログインされています。";
	
	/**
	 * コンストラクタ
	 * @param userService ユーザーサービス
	 * @param loginService ログインサービス
	 */
	@Autowired
	public SigninController(
			UserService userService,
			LoginService loginService) {
		// コンストラクタ
		this.userService = userService;
		this.loginService = loginService;
	}
	
	/**
	 * サインイン受信
	 * @param userLoginForm サインインフォーム
	 * @param result 結果
	 * @param redirectAttributes リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping
	public String index(
			@Validated UserLoginForm userLoginForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		// サインイン
		
		try {
			UserIdStatus userIdStatus = new UserIdStatus(0);
			LoginIdStatus loginIdStatus = new LoginIdStatus(0);
			
			// エラーチェック
			userIdStatus = this.checkSignin(userLoginForm, result, redirectAttributes);
			if( WebFunctions.isNotNull(userIdStatus) && userIdStatus.isError() ) {
				// エラーの場合何もしない
				return WebConsts.URL_REDIRECT_ROOM_INDEX;
			}
			
			// サインイン情報登録
			loginIdStatus = this.addSignin(userIdStatus);
			if( WebFunctions.isNotNull(loginIdStatus) && !loginIdStatus.isError() ) {
				// ログインIDをWebに登録
				redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, loginIdStatus.getId());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// room画面表示
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * サインインチェック
	 * @param userLoginForm ログインフォーム
	 * @param result 結果
	 * @param redirectAttributes リダイレクト
	 * @return ログインIDクラス
	 */
	private UserIdStatus checkSignin(
			UserLoginForm userLoginForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		// サインインエラーチェック
		if( result.hasErrors() ) {
			// エラーあり
			redirectAttributes.addFlashAttribute(
					WebConsts.BIND_NOTICE_ERROR, SIGNIN_MESSAGE_ERROR_LOGIN);
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		
		// エラーなし
		UserIdStatus userIdStatus = new UserIdStatus(
				this.userService.selectId_byNameEmailPasswd(
						new UserNameEmailPassword(
						userLoginForm.getName(), 
						userLoginForm.getEmail(), 
						userLoginForm.getPasswd())
				));
		
		// サインイン内容チェック
		if( userIdStatus.isError() ) {
			// ユーザ名、Eメール、パスワード一致しない[ERROR]
			redirectAttributes.addFlashAttribute(
					WebConsts.BIND_NOTICE_ERROR, SIGNIN_MESSAGE_ERROR_LOGIN);
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		
		if( this.loginService.isSelect_byUserId(userIdStatus) ) {
			// ログイン情報が既に存在する[ERROR]
			redirectAttributes.addFlashAttribute(
					WebConsts.BIND_NOTICE_ERROR, SIGNIN_MESSAGE_ERROR_ALREADY_LOGIN);
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		
		// サインインOK
		return userIdStatus;
	}
	
	/**
	 * サインイン情報登録処理
	 * @param user_id ユーザID
	 * @return ログインIDクラス
	 */
	private LoginIdStatus addSignin(UserIdStatus userStatus) {
		// サインイン情報登録
		LoginModel loginModel = new LoginModel(
				 new LoginIdStatus(0),
				 new RoomIdStatus(0),
				 userStatus,
				 LocalDateTime.now());
		return new LoginIdStatus(loginService.save_returnId(loginModel));
	}
}
