package com.example.demo.app.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.form.RoomCreateForm;
import com.example.demo.app.form.RoomUserForm;

/**
 * ---------------------------------------------------------------------------
 * 【部屋生成フォームコントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/createroom_form")
public class CreateRoomFormController {

	/**
	 * コンストラクタ
	 */
	@Autowired
	public CreateRoomFormController() {
		// コンストラクタ
	}
	
	/**
	 * ルーム作成受信
	 * @param roomUserForm: ルーム情報フォーム
	 * @param roomCreateForm: ルーム作成フォーム
	 * @param model
	 * @return Webパス(room/create_room)
	 */
	@PostMapping
	public String index(
			RoomUserForm roomUserForm,
			RoomCreateForm roomCreateForm,
			Model model) {
		// ルーム作成フォーム
		
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
		model.addAttribute(WebConsts.BIND_TITLE, "ルーム作成");
		model.addAttribute(WebConsts.BIND_CONT, "各項目を入力してください。");
	}
	
}
