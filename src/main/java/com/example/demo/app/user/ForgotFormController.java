package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserForgotForm;

/**
 * ---------------------------------------------------------------------------
 * 【パスワード変更フォームコントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/forgot_form")
public class ForgotFormController {

	/**
	 * コンストラクタ
	 */
	@Autowired
	public ForgotFormController() {
		// コンストラクタ
	}
	
	/**
	 * パスワード変更受信
	 * @param userForgotForm: パスワード変更フォーム
	 * @param model: モデル
	 * @return Webパス(user/forgot_form)
	 */
	@PostMapping
	public String index(
			UserForgotForm userForgotForm,
			Model model) {
		// パスワード変更フォーム画面
		
		// パスワード変更画面設定
		this.setForgot_form(model);
		return WebConsts.URL_USER_FORGOT_FORM;
	}
	
	/**
	 * パスワード変更受信
	 * @param userForgotForm: パスワード変更フォーム
	 * @param model: モデル
	 * @return Webパス(user/forgot_form)
	 */
	@GetMapping
	public String forgot_form_goback(
			UserForgotForm userForgotForm,
			Model model) {
		// TODO パスワード変更フォーム画面
		
		// パスワード変更画面設定
		this.setForgot_form(model);
		return WebConsts.URL_USER_FORGOT_FORM;
	}
	
	/**
	 * パスワード変更画面設定
	 * @param model
	 */
	private void setForgot_form(Model model) {
		// パスワード変更設定
		model.addAttribute(WebConsts.BIND_TITLE, "パスワード変更");
		model.addAttribute(WebConsts.BIND_CONT, "各項目を入力してください。");
	}
}
