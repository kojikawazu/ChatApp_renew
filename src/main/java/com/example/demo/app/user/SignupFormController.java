package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserSignupForm;
import com.example.demo.common.log.ChatAppLogger;

/**
 * ---------------------------------------------------------------------------
 * 【ユーザサインアップフォームコントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signup_form")
public class SignupFormController implements SuperUserController {
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** サインアップフォームタイトル */
	public static String SIGNUP_FORM_TITTLE = "サインアップ";
	
	/** サインアップフォームメッセージ */
	public static String SIGNUP_FORM_MESSAGE = "各項目を入力してください。";
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public SignupFormController() {
		
	}
	
	/**
	 * サインアップ受信
	 * @param userSignupForm サインアップフォーム
	 * @param model          モデル
	 * @return Webパス(signup_form/index)
	 */
	@PostMapping
	public String index(
			UserSignupForm userSignupForm,
			Model model) {
		this.appLogger.info("サインアップフォーム受信...");
		this.setSignup_form(model);
		return WebConsts.URL_USER_SIGNUP_FORM;
	}
	
	/**
	 * サインアップ戻り受信
	 * @param userSignupForm サインアップフォーム
	 * @param model          モデル
	 * @return Webパス(user/signup_form)
	 */
	@GetMapping
	public String signup_form_goback(
			UserSignupForm userSignupForm,
			Model model) {
		this.appLogger.info("サインアップフォームへ戻る");
		this.setSignup_form(model);
		return WebConsts.URL_USER_SIGNUP_FORM;
	}
	
	/**
	 * サインアップ画面設定
	 * @param model
	 */
	private void setSignup_form(Model model) {
		// サインアップ画面設定
		model.addAttribute(WebConsts.BIND_TITLE, SIGNUP_FORM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  SIGNUP_FORM_MESSAGE);
	}
}
