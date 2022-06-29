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
import com.example.demo.app.status.LoginIdStatus;
import com.example.demo.app.status.UserIdStatus;

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
	 * コンストラクタ
	 * @param userService
	 * @param loginService
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
	 * @param userLoginForm: サインインフォーム
	 * @param result: 結果
	 * @param redirectAttributes: リダイレクト
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
			userIdStatus = this.check_signin(userLoginForm, result, redirectAttributes);
			if( WebFunctions.isNotNull(userIdStatus) && userIdStatus.isError() ) {
				// 何もしない
				return WebConsts.URL_REDIRECT_ROOM_INDEX;
			}
			
			// サインイン情報登録
			loginIdStatus = this.addSignin(userIdStatus);
			if( WebFunctions.isNotNull(loginIdStatus) && !loginIdStatus.isError() ) {
				redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, loginIdStatus.getId());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * サインインチェック
	 * @param userLoginForm: ログインフォーム
	 * @param result: 結果
	 * @param redirectAttributes: リダイレクト
	 * @return
	 */
	private UserIdStatus check_signin(
			UserLoginForm userLoginForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		// サインインエラーチェック
		UserIdStatus userIdStatus = new UserIdStatus(0);
		
		if( result.hasErrors() ) {
			// エラーあり
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "ログインに失敗しました。");
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		
		userIdStatus = new UserIdStatus(
				this.userService.selectId_byNameEmailPasswd(
						userLoginForm.getName(), 
						userLoginForm.getEmail(), 
						userLoginForm.getPasswd())
				);
		if( userIdStatus.isError() ) {
			// ユーザ名、Eメール、パスワード一致しない
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "ログインに失敗しました。");
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		
		if( this.loginService.isSelect_byUserId(userIdStatus.getId()) ) {
			// ログイン情報が既に存在する
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "既にログインされています。");
			return new UserIdStatus(WebConsts.ERROR_NUMBER);
		}
		
		// サインインOK
		return userIdStatus;
	}
	
	/**
	 * サインイン情報登録処理
	 * @param user_id: ユーザID
	 * @return ログインID
	 */
	private LoginIdStatus addSignin(UserIdStatus userStatus) {
		// サインイン情報登録
		LoginModel loginModel = new LoginModel(
				 0,
				 0,
				 userStatus.getId(),
				 LocalDateTime.now());
		return new LoginIdStatus(loginService.save_returnId(loginModel));
	}
}
