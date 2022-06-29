package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserLogoutForm;
import com.example.demo.app.service.LoginService;

/**
 * ---------------------------------------------------------------------------
 * 【サインアウト実行コントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/logout")
public class SignoutController {

	/**
	 * サービス
	 */
	private LoginService loginService;

	/**
	 * コンストラクタ
	 * @param loginService
	 */
	@Autowired
	public SignoutController(
			LoginService loginService) {
		// コンストラクタ
		this.loginService = loginService;
	}
	
	/**
	 * サインアウト受信
	 * @param userLogoutForm: ログアウトフォーム
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping
	public String index(
			UserLogoutForm userLogoutForm,
			RedirectAttributes redirectAttributes) {
		// サインアウト
		
		// ログイン情報削除
		this.loginService.delete(userLogoutForm.getId());
		redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, 0);
		
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
}
