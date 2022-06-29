package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserForgotForm;
import com.example.demo.app.service.UserService;

/**
 * ---------------------------------------------------------------------------
 * 【パスワード変更確認コントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/forgot_complete")
public class ForgotCompleteController {

	/**
	 * サービス
	 */
	private UserService userService;
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public ForgotCompleteController(
			UserService userService) {
		// コンストラクタ
		this.userService = userService;
	}
	
	/**
	 * パスワード変更処理受信
	 * @param userForgotForm: パスワード変更フォーム
	 * @param result: 結果
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス(redirect;/room)
	 */
	@PostMapping
	public String index(
			@Validated UserForgotForm userForgotForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		// TODO パスワード変更処理
		
		// エラーチェック
		if(result.hasErrors()) {
			// 何もしない
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// パスワード変更
		this.userService.update_passwd(userForgotForm.getName(), userForgotForm.getEmail(), userForgotForm.getNew_passwd());
		redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_SUCCESS, "パスワードを変更しました。");
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
}
