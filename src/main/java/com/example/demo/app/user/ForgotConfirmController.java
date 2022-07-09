package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserForgotForm;
import com.example.demo.common.service.UserService;
import com.example.demo.common.word.UserNameEmail;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ---------------------------------------------------------------------------
 * 【パスワード変更確認コントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/forgot_confirm")
public class ForgotConfirmController implements SuperUserController {

	/**
	 * フィールド 
	 * 
	 */
	
	/** パスワード変更確認画面タイトル */
	public static String FORGOT_PASSWORD_CONFIRM_TITTLE = "パスワード変更確認";
	
	/** パスワード変更確認画面メッセージ */
	public static String FORGOT_PASSWORD_CONFIRM_MESSAGE = "これでよろしいですか？";
	
	/**
	 * bind関係
	 */
	
	/** ユーザー名の入力エラー(bindキー) */
	private static final String BIND_ERROR_MESSAGE_NAME = "error_message_user_name";
	
	/** Emailの入力エラー(bindキー) */
	private static final String BIND_ERROR_MESSAGE_EMAIL = "error_message_email";
	
	/** 忘れた時用のパスワードの入力エラー(bindキー) */
	private static final String BIND_ERROR_MESSAGE_FORGOT_PASSWD = "error_message_forgot_passwd";
	
	/**
	 * エラーメッセージ
	 */
	
	/** DBに登録データなし */
	public static final String ERROR_FORGOTPASSWD_NAMEEMAIL = "登録している名前とメールアドレスがありません。";
	
	/** DBと忘れた時用のパスワード不一致 */
	public static final String ERROR_FORGOTPASSWD = "忘れた時のパスワードが違います。";
	
	/** ユーザー名の入力エラー */
	private static final String ERROR_MESSAGE_NAME = "正しい名前を入力してください。";
	
	/** Emailの入力エラー */
	private static final String ERROR_MESSAGE_EMAIL = "正しいメールアドレスを入力してください。";
	
	/** 忘れた時用のパスワードの入力エラー */
	private static final String ERROR_MESSAGE_FORGOT_PASSWD = "正しいパスワードを入力してください。";
	
	/**
	 * フォームクラス
	 */
	
	/** 忘れた時用の入力フォームクラス */
	private static final String USER_FORGOT_FORM_NAME = "userForgotForm";
	
	/**
	 * サービス
	 */
	private UserService userService;
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public ForgotConfirmController(
			UserService userService) {
		// コンストラクタ
		this.userService = userService;
	}
	
	
	/**
	 * パスワード変更確認受信
	 * @param userForgotForm パスワード変更フォーム
	 * @param result 結果
	 * @param model モデル
	 * @return: Webパス(user/forgot_form, 
	 * 				   user/forgot_confirm)
	 */
	@PostMapping
	public String index(
			@Validated UserForgotForm userForgotForm,
			BindingResult result,
			Model model) {
		// パスワード変更確認
		
		// エラーチェック
		if(!isCheck_changepasswd(userForgotForm, result, model)) {
			// パスワード変更画面へ
			return WebConsts.URL_USER_FORGOT_FORM;
		}
		
		// パスワード変更確認画面へ
		this.setForgot_confirm(userForgotForm, model);
		return WebConsts.URL_USER_FORGOT_CONFIRM;
	}
	
	/**
	 * パスワード変更確認チェック
	 * @param userForgotForm パスワード変更フォーム
	 * @param result 結果
	 * @param model モデル
	 * @return true OK false NG
	 */
	private boolean isCheck_changepasswd(
			UserForgotForm userForgotForm,
			BindingResult result,
			Model model) {
		// パスワード変更確認チェック
		if(result.hasErrors()) {
			// エラーあり
			this.setErrorMessage(result, model);
			this.setForgot_form(model);
			return WebConsts.CHECK_COMMON_NG;
		}
		
		if( !this.userService.isSelect_byNameEmail(
				new UserNameEmail(
						userForgotForm.getName(), 
						userForgotForm.getEmail()))) {
			// ユーザ名、Eメール一致しない
			model.addAttribute(WebConsts.BIND_NOTICE_ERROR, ERROR_FORGOTPASSWD_NAMEEMAIL);
			this.setForgot_form(model);
			return WebConsts.CHECK_COMMON_NG;
		}
		if( !this.userService.isSelect_byNameEmailForgotPW(
				new UserNameEmailPassword(
						userForgotForm.getName(), 
						userForgotForm.getEmail(), 
						userForgotForm.getForgot_passwd() ) ) ) {
			// ユーザ名、Eメール、忘れたとき用パスワード一致しない
			model.addAttribute(WebConsts.BIND_NOTICE_ERROR, ERROR_FORGOTPASSWD);
			this.setForgot_form(model);
			return WebConsts.CHECK_COMMON_NG;
		}
		
		// パスワード確認OK
		return WebConsts.CHECK_COMMON_OK;
	}
	
	/**
	 * エラー時の文字を設定
	 * @param restult 結果クラス
	 * @param model モデル
	 */
	private void setErrorMessage(BindingResult result, Model model) {
		// 入力エラー設定
		
		// 名前のエラーチェック
		if( result.hasFieldErrors(USER_FORM_NAME) ) {
			// 名前入力エラー
			model.addAttribute(BIND_ERROR_MESSAGE_NAME, ERROR_MESSAGE_NAME);
		}
		
		// Emailのエラーチェック
		if( result.hasFieldErrors(USER_FORM_EMAIL) ) {
			// 名前入力エラー
			model.addAttribute(BIND_ERROR_MESSAGE_EMAIL, ERROR_MESSAGE_EMAIL);
		}
		
		// 忘れた時用のエラーチェック
		if( result.hasFieldErrors(USER_FORM_FORGOT_PASSWD) ) {
			// 名前入力エラー
			model.addAttribute(BIND_ERROR_MESSAGE_FORGOT_PASSWD, ERROR_MESSAGE_FORGOT_PASSWD);
		}
	}
	
	
	/**
	 * パスワード変更画面へ
	 * @param model モデル
	 */
	private void setForgot_form(Model model) {
		// パスワード変更設定
		model.addAttribute(WebConsts.BIND_TITLE, ForgotFormController.FORGOT_PASSWORD_FORM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  ForgotFormController.FORGOT_PASSWORD_FORM_MESSAGE);
	}
	
	/**
	 * パスワード変更確認画面へ
	 * @param userForgotForm パスワード変更フォーム
	 * @param model モデル
	 */
	private void setForgot_confirm(UserForgotForm userForgotForm, Model model) {
		// パスワード変更設定
		model.addAttribute(WebConsts.BIND_TITLE, FORGOT_PASSWORD_CONFIRM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  FORGOT_PASSWORD_CONFIRM_MESSAGE);
		model.addAttribute(USER_FORGOT_FORM_NAME, userForgotForm);
	}
}
