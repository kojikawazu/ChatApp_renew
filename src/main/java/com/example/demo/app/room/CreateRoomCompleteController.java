package com.example.demo.app.room;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.form.RoomCreateForm;
import com.example.demo.app.service.CommentService;
import com.example.demo.app.service.EnterService;
import com.example.demo.app.service.LoginService;
import com.example.demo.app.service.RoomService;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;

/**
 * ---------------------------------------------------------------------------
 * 【部屋生成実行コントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/room_complete")
public class CreateRoomCompleteController {

	/**
	 * サービス
	 */
	private RoomService roomService;
	private CommentService commentService;
	private LoginService loginService;
	private EnterService enterService;
	
	/**
	 * コンストラクタ
	 * @param loginService
	 * 
	 */
	@Autowired
	public CreateRoomCompleteController(
			RoomService roomService,
			CommentService commentService,
			LoginService loginService,
			EnterService enterService) {
		// コンストラクタ
		this.roomService = roomService;
		this.commentService = commentService;
		this.loginService = loginService;
		this.enterService = enterService;
	}
	
	
	/**
	 * ルーム作成処理受信
	 * @param roomCreateForm: ルーム作成フォーム
	 * @param result: 結果
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス(room/create_room, redirect:/chat)
	 */
	@PostMapping
	public String index(
			@Validated RoomCreateForm roomCreateForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		// ルーム作成へ
		
		// エラーチェック
		if(result.hasErrors()) {
			// ルーム作成画面設定
			this.setCreateroom_form(model);
			
			// ルーム作成画面へ
			model.addAttribute(WebConsts.BIND_LOGIN_ID, roomCreateForm.getLogin_id());
			return WebConsts.URL_ROOM_CREATE_FORM;
		}
		
		// ルームの生成
		LoginModel loginModel = this.loginService.select(new LoginIdStatus(roomCreateForm.getLogin_id()));
		int room_id = this.setRoom(loginModel, roomCreateForm);
		
		// ログイン情報のルーム番号の更新
		this.loginService.updateRoomId_byId(
				new RoomIdStatus(room_id), 
				new LoginIdStatus(roomCreateForm.getLogin_id()));
		
		// 入室情報の生成
		int enter_id = this.setEnter_createroom(loginModel, roomCreateForm, room_id);
		
		// 部屋生成コメントの追加
		this.setComment_createroom(loginModel, room_id);
		
		// チャット画面へ
		redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, enter_id);
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * ルーム追加処理
	 * @param loginModel: ログインモデル
	 * @param roomCreateForm: ルーム生成フォーム
	 * @return
	 */
	private int setRoom(LoginModel loginModel, RoomCreateForm roomCreateForm) {
		// ルームの追加
		RoomModel roomModel = new RoomModel();
		roomModel.setName(roomCreateForm.getName());
		roomModel.setComment(roomCreateForm.getComment());
		roomModel.setMax_roomsum(roomCreateForm.getMax_roomsum());
		roomModel.setTag(roomCreateForm.getTag());
		roomModel.setUser_id(loginModel.getUser_id());
		roomModel.setCreated(LocalDateTime.now());
		roomModel.setUpdated(LocalDateTime.now());
		return this.roomService.save_returnId(roomModel);
	}
	
	/**
	 * 入室情報の追加
	 * @param loginModel: ログインモデル
	 * @param roomCreateForm: ルーム生成フォーム
	 * @param room_id: ルームID
	 * @return 入室ID
	 */
	private int setEnter_createroom(LoginModel loginModel, RoomCreateForm roomCreateForm, int room_id) {
		// 入室情報の追加
		EnterModel enterModel = new EnterModel();
		enterModel.setRoom_id(room_id);
		enterModel.setUser_id(loginModel.getUser_id());
		enterModel.setManager_id(loginModel.getUser_id());
		enterModel.setMax_sum(roomCreateForm.getMax_roomsum());
		enterModel.setCreated(LocalDateTime.now());
		return this.enterService.save_returnId(enterModel);
	}
	
	/**
	 * 部屋生成コメントの追加
	 * @param loginModel: ログインモデル
	 * @param room_id: ルームID
	 */
	private void setComment_createroom(LoginModel loginModel, int room_id) {
		// 部屋生成コメントの追加
		CommentModel commentModel = new CommentModel();
		commentModel.setRoom_id(room_id);
		commentModel.setUser_id(loginModel.getId());
		commentModel.setComment("部屋が作られました。");
		commentModel.setCreated(LocalDateTime.now());
		this.commentService.save(commentModel);
	}
	
	/**
	 * ルーム作成画面設定
	 * @param model
	 */
	private void setCreateroom_form(Model model) {
		// ルーム作成の設定
		model.addAttribute(WebConsts.BIND_TITLE, "ルーム作成");
		model.addAttribute(WebConsts.BIND_CONT, "各項目を入力してください。");
	}
}
