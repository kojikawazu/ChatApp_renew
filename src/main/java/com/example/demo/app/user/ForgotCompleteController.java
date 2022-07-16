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
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.UserService;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ---------------------------------------------------------------------------
 * 【パスワード変更確認コントローラ】
* @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/forgot_complete")
public class ForgotCompleteController implements SuperUserController {

	/**
	 * サービス
	 */
	private UserService userService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** パスワード変更実行メッセージ */
	public static String FORGOT_PASSWORD_ACTION_MESSAGE = "パスワードを変更しました。";
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public ForgotCompleteController(
			UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * パスワード変更処理受信
	 * @param userForgotForm     パスワード変更フォーム
	 * @param result             結果
	 * @param model              モデル
	 * @param redirectAttributes リダイレクト
	 * @return Webパス(redirect;/room)
	 */
	@PostMapping
	public String index(
			@Validated UserForgotForm userForgotForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("パスワード変更実行受信...");
		
		// エラーチェック
		if(result.hasErrors()) {
			// 何もしない
			this.appLogger.error("バリデーションエラー: " + result);
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// パスワード変更
		this.userService.update_passwd(
				new UserNameEmailPassword(
						userForgotForm.getName(), 
						userForgotForm.getEmail(), 
						userForgotForm.getNew_passwd()));
		
		// パスワード変更実行通知
		redirectAttributes.addFlashAttribute(
				WebConsts.BIND_NOTICE_SUCCESS, 
				FORGOT_PASSWORD_ACTION_MESSAGE);
		
		this.appLogger.successed("パスワード変更実行成功");
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
}
