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
import com.example.demo.app.service.UserService;
import com.example.demo.common.word.UserNameEmail;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ---------------------------------------------------------------------------
 * 【パスワード変更確認コントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/forgot_confirm")
public class ForgotConfirmController {

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
	 * @param userForgotForm: パスワード変更フォーム
	 * @param result: 結果
	 * @param model: モデル
	 * @return: Webパス(user/forgot_form, user/forgot_confirm)
	 */
	@PostMapping
	public String index(
			@Validated UserForgotForm userForgotForm,
			BindingResult result,
			Model model) {
		// TODO パスワード変更確認
		
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
	 * @param userForgotForm: パスワード変更フォーム
	 * @param result: 結果
	 * @param model: モデル
	 * @return true: OK false: NG
	 */
	private boolean isCheck_changepasswd(
			UserForgotForm userForgotForm,
			BindingResult result,
			Model model) {
		// TODO パスワード変更確認チェック
		if(result.hasErrors()) {
			// エラーあり
			this.setForgot_form(model);
			return false;
		}
		
		if( !this.userService.isSelect_byNameEmail(
				new UserNameEmail(
						userForgotForm.getName(), 
						userForgotForm.getEmail()))) {
			// ユーザ名、Eメール一致しない
			model.addAttribute(WebConsts.BIND_NOTICE_ERROR, WebConsts.ERROR_FORGOTPASSWD_NAMEEMAIL);
			this.setForgot_form(model);
			return false;
		}
		if( !this.userService.isSelect_byNameEmailForgotPW(
				new UserNameEmailPassword(
						userForgotForm.getName(), 
						userForgotForm.getEmail(), 
						userForgotForm.getForgot_passwd() ) ) ) {
			// ユーザ名、Eメール、忘れたとき用パスワード一致しない
			model.addAttribute(WebConsts.BIND_NOTICE_ERROR, WebConsts.ERROR_FORGOTPASSWD);
			this.setForgot_form(model);
			return false;
		}
		
		// パスワード確認OK
		return true;
	}
	
	/**
	 * パスワード変更画面へ
	 * @param model
	 */
	private void setForgot_form(Model model) {
		// パスワード変更設定
		model.addAttribute(WebConsts.BIND_TITLE, "パスワード変更");
		model.addAttribute(WebConsts.BIND_CONT, "各項目を入力してください。");
	}
	
	/**
	 * パスワード変更確認画面へ
	 * @param userForgotForm: パスワード変更フォーム
	 * @param model: モデル
	 */
	private void setForgot_confirm(UserForgotForm userForgotForm, Model model) {
		// パスワード変更設定
		model.addAttribute(WebConsts.BIND_TITLE, "パスワード変更確認");
		model.addAttribute(WebConsts.BIND_CONT, "これでよろしいですか？");
		model.addAttribute("userForgotForm", userForgotForm);
	}
}
