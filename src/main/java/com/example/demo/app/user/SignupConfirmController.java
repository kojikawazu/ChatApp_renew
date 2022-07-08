package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserSignupForm;

/**
 * ---------------------------------------------------------------------------
 * 【ユーザサインアップ確認コントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signup_confirm")
public class SignupConfirmController implements SuperUserController {

	/**
	 * フィールド 
	 * 
	 */
	/** サインアップ確認画面タイトル */
	public static String SIGNUP_CONFIRM_TITTLE = "サインアップ確認";
	
	/** サインアップ確認画面メッセージ */
	public static String SIGNUP_CONFIRM_MESSAGE = "このユーザーでよろしいでしょうか？";
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public SignupConfirmController() {
		// コンストラクタ
	}
	
	/**
	 * サインアップ確認受信
	 * @param userSignupForm サインアップフォーム
	 * @param result 結果
	 * @param model モデル
	 * @return Webパス(user/signup_confirm, 
	 * 				user/signup_form)
	 */
	@PostMapping
	public String index(
			@Validated UserSignupForm userSignupForm,
			BindingResult result,
			Model model) {
		// サインアップ確認
		if(result.hasErrors()) {
			this.setSignup_form(model);
			// サインアップフォーム画面へ
			return WebConsts.URL_USER_SIGNUP_FORM;
		}
		// サインアップ確認画面へ
		setSignup_confirm(model);
		return WebConsts.URL_USER_SIGNUP_CONFIRM;
	}
	
	/**
	 * サインアップ画面設定
	 * @param model
	 */
	private void setSignup_form(Model model) {
		// サインアップ画面設定
		model.addAttribute(WebConsts.BIND_TITLE, SignupFormController.SIGNUP_FORM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  SignupFormController.SIGNUP_FORM_MESSAGE);
	}
	
	/**
	 * サインアップ確認画面設定
	 * @param model
	 */
	private void setSignup_confirm(Model model) {
		// パスワード確認画面設定
		model.addAttribute(WebConsts.BIND_TITLE, SIGNUP_CONFIRM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  SIGNUP_CONFIRM_MESSAGE);
	}
}
