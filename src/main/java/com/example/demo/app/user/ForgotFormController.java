package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserForgotForm;
import com.example.demo.common.log.ChatAppLogger;

/**
 * ---------------------------------------------------------------------------
 * 【パスワード変更フォームコントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/forgot_form")
public class ForgotFormController implements SuperUserController {

	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** パスワード変更フォーム画面タイトル */
	public static String FORGOT_PASSWORD_FORM_TITTLE = "パスワード変更";
	
	/** パスワード変更フォーム画面メッセージ */
	public static String FORGOT_PASSWORD_FORM_MESSAGE = "各項目を入力してください。";
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public ForgotFormController() {
		
	}
	
	/**
	 * パスワード変更受信
	 * @param userForgotForm パスワード変更フォーム
	 * @param model          モデル
	 * @return Webパス(user/forgot_form)
	 */
	@PostMapping
	public String index(
			UserForgotForm userForgotForm,
			Model model) {
		this.appLogger.info("パスワード変更フォーム受信...");
		
		// パスワード変更画面設定
		this.setForgot_form(model);
		return WebConsts.URL_USER_FORGOT_FORM;
	}
	
	/**
	 * パスワード変更受信
	 * @param userForgotForm パスワード変更フォーム
	 * @param model          モデル
	 * @return Webパス(user/forgot_form)
	 */
	@GetMapping
	public String forgot_form_goback(
			UserForgotForm userForgotForm,
			Model model) {
		this.appLogger.info("パスワード変更フォームへ戻る");
		
		// パスワード変更画面設定
		this.setForgot_form(model);
		return WebConsts.URL_USER_FORGOT_FORM;
	}
	
	/**
	 * パスワード変更画面設定
	 * @param model
	 */
	private void setForgot_form(Model model) {
		// パスワード変更画面設定
		model.addAttribute(WebConsts.BIND_TITLE, FORGOT_PASSWORD_FORM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  FORGOT_PASSWORD_FORM_MESSAGE);
	}
}
