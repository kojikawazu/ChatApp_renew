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
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.UserService;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.UserNameEmail;

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
	 * サービス
	 */
	private UserService userService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** サインアップ確認画面タイトル */
	public static String SIGNUP_CONFIRM_TITTLE = "サインアップ確認";
	
	/** サインアップ確認画面メッセージ */
	public static String SIGNUP_CONFIRM_MESSAGE = "このユーザーでよろしいでしょうか？";
	
	/**
	 * コンストラクタ
	 * @param userService ユーザーサービス
	 */
	@Autowired
	public SignupConfirmController(
			UserService userService) {
		this.userService = userService;
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
		this.appLogger.start("サインアップ確認受信...");
		
		if( !this.isCheckForm(userSignupForm, result, model) ) {
			// [ERROR]
			// サインアップフォーム画面へ
			this.appLogger.error("失敗...サインアップフォームへ");
			this.setSignup_form(model);
			return WebConsts.URL_USER_SIGNUP_FORM;
		}
		// サインアップ確認画面へ
		this.setSignup_confirm(model);
		
		this.appLogger.successed("サインアップ確認成功");
		return WebConsts.URL_USER_SIGNUP_CONFIRM;
	}
	
	/**
	 * サインアップ確認
	 * @param userSignupForm  サインアップフォームクラス
	 * @param result          バリデーション結果
	 * @return true OK false NG
	 */
	private boolean isCheckForm(
			UserSignupForm userSignupForm,
			BindingResult result,
			Model model) {
		this.appLogger.start("サインアップ確認チェック...");
		
		// バリデーションチェック
		if(result.hasErrors()) {
			// [ERROR]
			this.appLogger.error("バリデーションエラー: " + result);
			return false;
		}
		
		UserNameEmail userNameEmail = new UserNameEmail(
				new NameWord(userSignupForm.getName()), 
				new EmailWord(userSignupForm.getEmail()));
		// DBチェック
		if(this.userService.isSelect_byNameOrEmail(userNameEmail)) {
			// [ERROR]
			this.appLogger.error("ユーザー名又はEメール存在する: user:  " + userSignupForm.getName());
			this.appLogger.error("                      : email: " + userSignupForm.getEmail());
			
			model.addAttribute(WebConsts.BIND_NOTICE_ERROR, 
					"ユーザー名、またはEメールは既に登録済です。");
			return false;
		}
		
		this.appLogger.successed("サインアップ確認チェックOK");
		return true;
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
