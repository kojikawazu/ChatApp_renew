package com.example.demo.app.room;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.config.WebFunctions;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.form.RoomCreateForm;
import com.example.demo.app.form.RoomUserForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.service.UserService;
import com.example.demo.common.status.LoginIdStatus;

/**
 * ---------------------------------------------------------------------------
 * 【部屋生成フォームコントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/createroom_form")
public class CreateRoomFormController implements SuperRoomController {

	/**
	 * サービス
	 */
	private LoginService   loginService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** ルーム生成フォーム画面タイトル */
	public static String CREATE_ROOM_FORM_TITTLE = "ルーム作成";
	
	/** ルーム生成フォーム画面メッセージ */
	public static String CREATE_ROOM_FORM_MESSAGE = "各項目を入力してください。";
	
	/**
	 * コンストラクタ
	 * @param loginService
	 */
	@Autowired
	public CreateRoomFormController(
			LoginService   loginService) {
		this.loginService   = loginService;
	}
	
	/**
	 * ルーム作成受信
	 * @param roomUserForm         ルーム情報フォーム
	 * @param roomCreateForm       ルーム作成フォーム
	 * @param model                モデル
	 * @param redirectAttributes   リダイレクト
	 * @return Webパス(room/create_room)
	 */
	@PostMapping
	public String index(
			RoomUserForm roomUserForm,
			RoomCreateForm roomCreateForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// ルーム作成フォーム
		this.appLogger.info("ルーム作成フォーム受信... loginId: " + roomUserForm.getLogin_id());
		
		// ログイン情報更新日付チェック
		if(!this.isLoginUpdated(roomUserForm, redirectAttributes)) {
			// [ERROR]
			// 更新日付だいぶ経っているのでルーム画面へリダイレクト
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ルーム作成画面設定
		this.setCreateroom_form(roomUserForm, model);
		
		return WebConsts.URL_ROOM_CREATE_FORM;
	}
	
	/**
	 * 更新日付チェック
	 * @param roomUserForm        ルームフォーム
	 * @param redirectAttributes  リダイレクト
	 * @return  true ログイン中 false ログイン更新日付経っている
	 */
	private boolean isLoginUpdated(RoomUserForm roomUserForm, RedirectAttributes redirectAttributes) {
		LoginIdStatus loginId = new LoginIdStatus(roomUserForm.getLogin_id());
		LoginModel loginModel = this.loginService.select(loginId);
		
		// 更新日付チェック
		if(!WebFunctions.checkDiffHour(
				loginModel.getUpdated(), WebConsts.TIME_TIMER_NUMBER_HOUR)) {
			// [ERROR]
			// 更新日付がだいぶ経っている
			this.loginService.delete(loginId);
			
			String encryptNumber = CommonEncript.encrypt(WebConsts.LOGIN_ID_INIT_NUMBER);
			redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_LOGIN_ID, encryptNumber);
			return WebConsts.TIME_RUNNING_OUT;
		}
		
		// 更新日付経ってない
		// 更新日付の更新
		this.loginService.updateUpdated_byId(
				LocalDateTime.now(), loginId);
		
		return WebConsts.TIME_WITHIN;
	}
	
	/**
	 * ルーム作成画面設定
	 * @param roomUserForm ルームフォーム
	 * @param model        モデル
	 */
	private void setCreateroom_form(RoomUserForm roomUserForm, Model model) {
		// ルーム作成画面設定
		model.addAttribute(WebConsts.BIND_LOGIN_ID, roomUserForm.getLogin_id());
		model.addAttribute(WebConsts.BIND_ENCRYPT_LOGIN_ID,
				CommonEncript.encrypt(roomUserForm.getLogin_id()));
		
		model.addAttribute(WebConsts.BIND_TITLE, CREATE_ROOM_FORM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  CREATE_ROOM_FORM_MESSAGE);
	}
}
