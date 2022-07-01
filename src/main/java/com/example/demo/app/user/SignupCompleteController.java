package com.example.demo.app.user;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.UserModel;
import com.example.demo.app.form.UserSignupForm;
import com.example.demo.app.service.LoginService;
import com.example.demo.app.service.UserService;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.PasswordWord;

/**
 * ---------------------------------------------------------------------------
 * 【ユーザサインアップ実行コントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signup_complete")
public class SignupCompleteController {

	/**
	 * サービス
	 */
	private UserService userService;
	private LoginService loginService;
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public SignupCompleteController(
			UserService userService,
			LoginService loginService) {
		// コンストラクタ
		this.userService = userService;
		this.loginService = loginService;
	}
	
	/**
	 * サインアップ処理受信
	 * @param userSignupForm: サインアップフォーム
	 * @param result: 結果
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping
	public String index(
			UserSignupForm userSignupForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		// サインアップ処理
		if(result.hasErrors()) {
			// 何もしない
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ユーザーの生成
		int user_id =  this.createUser(userSignupForm);
		// ログイン情報の追加
		int login_id = this.addSignin(user_id);
		redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
		
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * ユーザ追加処理
	 * @param userSignupForm: サインアップフォーム
	 * @return ユーザID
	 */
	private int createUser(UserSignupForm userSignupForm) {
		// ユーザの追加
		UserModel userModel = new UserModel(
				new UserIdStatus(0),
				new NameWord(userSignupForm.getName()),
				new EmailWord(userSignupForm.getEmail()),
				new PasswordWord(userSignupForm.getNew_passwd()),
				new PasswordWord(userSignupForm.getForgot_passwd()),
				LocalDateTime.now(),
				LocalDateTime.now());
		return this.userService.save_returnId(userModel);
	}
	
	/**
	 * サインイン情報登録処理
	 * @param user_id: ユーザID
	 * @return ログインID
	 */
	private int addSignin(int user_id) {
		// サインイン情報登録
		LoginModel loginModel = new LoginModel(
				new LoginIdStatus(0),
				new RoomIdStatus(0),
				new UserIdStatus(user_id),
				LocalDateTime.now()
				);
		return this.loginService.save_returnId(loginModel);
	}
	
}
