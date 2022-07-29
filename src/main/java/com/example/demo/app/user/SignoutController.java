package com.example.demo.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.UserLogoutForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.session.SessionLoginId;
import com.example.demo.common.status.LoginIdStatus;

/**
 * ---------------------------------------------------------------------------
 * 【サインアウト実行コントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/logout")
public class SignoutController implements SuperUserController {

	/**
	 * サービス
	 */
	private LoginService loginService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/**
	 * セッションクラス
	 */
	@Autowired
	SessionLoginId sessionLoginId;

	/**
	 * コンストラクタ
	 * @param loginService
	 */
	@Autowired
	public SignoutController(
			LoginService loginService) {
		this.loginService = loginService;
	}
	
	/**
	 * サインアウト受信
	 * @param userLogoutForm      ログアウトフォーム
	 * @param redirectAttributes  リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping
	public String index(
			UserLogoutForm userLogoutForm,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("サインアウト受信...");
		
		// ログイン情報削除
		LoginIdStatus login_id = this.deleteLoginInfo(userLogoutForm);
		
		// Web側：ログインID初期化
		this.setAttribute(redirectAttributes);
		
		this.appLogger.successed("サインアウト成功 : loginId : " + login_id.getId());
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * ログイン情報の削除
	 * @param userLogoutForm
	 * @return ログインID
	 */
	private LoginIdStatus deleteLoginInfo(UserLogoutForm userLogoutForm) {
		this.appLogger.start("ログイン情報の削除...");
		
		LoginIdStatus login_id = new LoginIdStatus(userLogoutForm.getId());
		this.loginService.delete(login_id);
		
		this.appLogger.successed("ログイン情報の削除成功: loginId: " + login_id.getId());
		return login_id;
	}
	
	/**
	 * Web側登録中のログインIDを初期化
	 * @param redirectAttributes
	 */
	private void setAttribute(RedirectAttributes redirectAttributes) {
		// Web側：ログインID初期化
		String encryptNumber = CommonEncript.encrypt(WebConsts.LOGIN_ID_INIT_NUMBER);
		redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_LOGIN_ID, encryptNumber);
		
		// セッション：ログインIDの削除
		this.sessionLoginId.setBlank();
	}
}
