package com.example.demo.app.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserSignupForm;

/**
 * ---------------------------------------------------------------------------
 * 【ユーザサインアップフォームコントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signup_form")
public class SignupFormController {

	//Log log = LogFactory.getLog(UserSignupController.class);
	//log.info("テストOK");
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public SignupFormController() {
		// コンストラクタ
	}
	
	/**
	 * サインアップ受信
	 * @param userSignupForm: サインアップフォーム
	 * @param model: モデル
	 * @return Webパス(signup_form/index)
	 */
	@PostMapping
	public String index(
			UserSignupForm userSignupForm,
			Model model) {
		// サインアップフォーム受信
		this.setSignup_form(model);
		return WebConsts.URL_USER_SIGNUP_FORM;
	}
	
	/**
	 * サインアップ戻り受信
	 * @param userSignupForm: サインアップフォーム
	 * @param model: モデル
	 * @return Webパス(user/signup_form)
	 */
	@GetMapping
	public String signup_form_goback(
			UserSignupForm userSignupForm,
			Model model) {
		// サインアップフォームに戻ってきた
		this.setSignup_form(model);
		return WebConsts.URL_USER_SIGNUP_FORM;
	}
	
	/**
	 * サインアップ画面設定
	 * @param model
	 */
	private void setSignup_form(Model model) {
		// サインアップ画面設定
		model.addAttribute(WebConsts.BIND_TITLE, "サインアップ");
		model.addAttribute(WebConsts.BIND_CONT, "各項目を入力してください。");
	}
}
