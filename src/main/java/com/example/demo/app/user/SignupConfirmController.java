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
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signup_confirm")
public class SignupConfirmController {

	/**
	 * コンストラクタ
	 */
	@Autowired
	public SignupConfirmController() {
		// コンストラクタ
	}
	
	/**
	 * サインアップ確認受信
	 * @param userSignupForm: サインアップフォーム
	 * @param result: 結果
	 * @param model: モデル
	 * @return Webパス(user/signup_confirm, user/signup_form)
	 */
	@PostMapping
	public String index(
			@Validated UserSignupForm userSignupForm,
			BindingResult result,
			Model model) {
		// サインアップ確認
		if(result.hasErrors()) {
			this.setSignup_form(model);
			// サインアップ画面へ
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
		// TODO パスワード変更設定
		model.addAttribute(WebConsts.BIND_TITLE, "サインアップ");
		model.addAttribute(WebConsts.BIND_CONT, "各項目を入力してください。");
	}
	
	/**
	 * サインアップ確認画面設定
	 * @param model
	 */
	private void setSignup_confirm(Model model) {
		// TODO パスワード変更設定
		model.addAttribute(WebConsts.BIND_TITLE, "サインアップ確認");
		model.addAttribute(WebConsts.BIND_CONT, "このユーザーでよろしいでしょうか？");
	}
}
