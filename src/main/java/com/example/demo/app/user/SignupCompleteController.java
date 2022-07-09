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
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.UserService;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.PasswordWord;

/**
 * ---------------------------------------------------------------------------
 * 【ユーザサインアップ実行コントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/signup_complete")
public class SignupCompleteController implements SuperUserController {

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
	 * @param userSignupForm サインアップフォーム
	 * @param result 結果
	 * @param model モデル
	 * @param redirectAttributes リダイレクト
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
		UserIdStatus userId =  this.createUser(userSignupForm);
		// ログイン情報の追加
		LoginIdStatus loginId = this.addSignIn(userId);
		redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, loginId.getId());
		
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * ユーザ追加処理
	 * @param userSignupForm サインアップフォーム
	 * @return ユーザID
	 */
	private UserIdStatus createUser(UserSignupForm userSignupForm) {
		// ユーザの追加
		UserModel userModel = new UserModel(
				new NameWord(userSignupForm.getName()),
				new EmailWord(userSignupForm.getEmail()),
				new PasswordWord(userSignupForm.getNew_passwd()),
				new PasswordWord(userSignupForm.getForgot_passwd()),
				LocalDateTime.now(),
				LocalDateTime.now()
				);
		return this.userService.save_returnId(userModel);
	}
	
	/**
	 * サインイン情報登録処理
	 * @param user_id ユーザID
	 * @return ログインID
	 */
	private LoginIdStatus addSignIn(UserIdStatus userId) {
		// サインイン情報登録
		LoginModel loginModel = new LoginModel(
				new RoomIdStatus(0),
				new UserIdStatus(userId.getId()),
				LocalDateTime.now()
				);
		return this.loginService.save_returnId(loginModel);
	}
	
}
