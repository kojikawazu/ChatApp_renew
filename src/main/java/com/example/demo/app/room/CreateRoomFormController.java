package com.example.demo.app.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.RoomCreateForm;
import com.example.demo.app.form.RoomUserForm;
import com.example.demo.common.log.ChatAppLogger;

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
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** ルーム生成フォーム画面タイトル */
	public static String CREATE_ROOM_FORM_TITTLE = "ルーム作成";
	
	/** ルーム生成フォーム画面メッセージ */
	public static String CREATE_ROOM_FORM_MESSAGE = "各項目を入力してください。";
	
	/**
	 * コンストラクタ
	 */
	@Autowired
	public CreateRoomFormController() {
		
	}
	
	/**
	 * ルーム作成受信
	 * @param roomUserForm   ルーム情報フォーム
	 * @param roomCreateForm ルーム作成フォーム
	 * @param model
	 * @return Webパス(room/create_room)
	 */
	@PostMapping
	public String index(
			RoomUserForm roomUserForm,
			RoomCreateForm roomCreateForm,
			Model model) {
		// ルーム作成フォーム
		this.appLogger.info("ルーム作成フォーム受信... loginId: " + roomUserForm.getLogin_id());
		
		// ルーム作成画面設定
		this.setCreateroom_form(model);
		model.addAttribute(WebConsts.BIND_LOGIN_ID, roomUserForm.getLogin_id());
		
		return WebConsts.URL_ROOM_CREATE_FORM;
	}
	
	/**
	 * ルーム作成画面設定
	 * @param model
	 */
	private void setCreateroom_form(Model model) {
		// ルーム作成画面設定
		model.addAttribute(WebConsts.BIND_TITLE, CREATE_ROOM_FORM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  CREATE_ROOM_FORM_MESSAGE);
	}
}
