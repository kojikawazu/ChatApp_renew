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
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;
import com.example.demo.common.word.RoomCommentWord;
import com.example.demo.common.word.RoomNameWord;
import com.example.demo.common.word.RoomTagWord;

/**
 * ---------------------------------------------------------------------------
 * 【部屋生成実行コントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/room_complete")
public class CreateRoomCompleteController implements SuperRoomController {
	
	/**
	 * サービス
	 */
	private RoomService    roomService;
	private CommentService commentService;
	private LoginService   loginService;
	private EnterService   enterService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/**
	 * コンストラクタ
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 * 
	 */
	@Autowired
	public CreateRoomCompleteController(
			RoomService    roomService,
			CommentService commentService,
			LoginService   loginService,
			EnterService   enterService) {
		this.roomService    = roomService;
		this.commentService = commentService;
		this.loginService   = loginService;
		this.enterService   = enterService;
	}
	
	/**
	 * ルーム作成処理受信
	 * @param roomCreateForm     ルーム作成フォーム
	 * @param result             結果
	 * @param model              モデル
	 * @param redirectAttributes リダイレクト
	 * @return Webパス(room/create_room, redirect:/chat)
	 */
	@PostMapping
	public String index(
			@Validated RoomCreateForm roomCreateForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("ルーム作成実行受信...");
		
		// エラーチェック
		if(result.hasErrors()) {
			// [ERROR]
			this.appLogger.error("バリデーションエラー: " + result);
			
			// ルーム作成画面設定
			this.setCreateroom_form(model);
			
			// ルーム作成画面へ
			model.addAttribute(WebConsts.BIND_LOGIN_ID, roomCreateForm.getLogin_id());
			return WebConsts.URL_ROOM_CREATE_FORM;
		}
		
		// ルームの生成
		LoginModel loginModel = this.loginService.select(
				new LoginIdStatus(roomCreateForm.getLogin_id()));
		RoomIdStatus room_id  = this.setRoom(loginModel, roomCreateForm);
		
		// サインイン情報のルーム番号の更新
		this.updateLoginInfo_roomId(room_id, roomCreateForm);
		
		// 入室情報の生成
		EnterIdStatus enter_id = this.setEnter_createroom(loginModel, roomCreateForm, room_id);
		
		// 部屋生成コメントの追加
		this.setComment_createroom(loginModel, room_id);
		
		// チャット画面へ
		String encryptNumber = CommonEncript.encrypt(enter_id.getId());
		redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_ENTER_ID, encryptNumber);
		
		this.appLogger.successed("ルーム生成実行成功: roomId: " + room_id.getId());
		this.appLogger.successed("               : enterId: " + enter_id.getId());
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * ルーム追加処理
	 * @param loginModel     サインインモデル
	 * @param roomCreateForm ルーム生成フォーム
	 * @return               ルームID
	 */
	private RoomIdStatus setRoom(LoginModel loginModel, RoomCreateForm roomCreateForm) {
		this.appLogger.start("ルーム生成...");
		
		RoomModel roomModel = new RoomModel(
				new RoomNameWord(roomCreateForm.getName()),
				new RoomCommentWord(roomCreateForm.getComment()),
				new RoomTagWord(roomCreateForm.getTag()),
				new RoomMaxNumber(roomCreateForm.getMax_roomsum()),
				new UserIdStatus(loginModel.getUser_id()),
				LocalDateTime.now(),
				LocalDateTime.now());
		RoomIdStatus roomIdStatus = this.roomService.save_returnId(roomModel);
		
		this.appLogger.successed("ルーム生成の成功: roomId: " + roomIdStatus.getId());
		return roomIdStatus;
	}
	
	/**
	 * サインイン情報のルーム番号の更新
	 * @param room_id
	 * @param roomCreateForm
	 */
	private void updateLoginInfo_roomId(RoomIdStatus room_id, RoomCreateForm roomCreateForm) {
		this.appLogger.start("サインイン情報のルーム番号の更新...");
		
		this.loginService.updateRoomId_byId(
				room_id, 
				new LoginIdStatus(roomCreateForm.getLogin_id()));
		
		this.appLogger.info("サインイン情報のルーム番号の更新: roomId:  " + room_id.getId());
		this.appLogger.info("サインイン情報のルーム番号の更新: loginId: " + roomCreateForm.getLogin_id());
	}
	
	/**
	 * 入室情報の追加
	 * @param loginModel     サインインモデル
	 * @param roomCreateForm ルーム生成フォーム
	 * @param room_id        ルームID
	 * @return               入室ID
	 */
	private EnterIdStatus setEnter_createroom(LoginModel loginModel, RoomCreateForm roomCreateForm, RoomIdStatus room_id) {
		this.appLogger.start("入室情報生成...");
		
		EnterModel enterModel = new EnterModel(
				room_id,
				new UserIdStatus(loginModel.getUser_id()),
				new UserIdStatus(loginModel.getUser_id()),
				LocalDateTime.now(),
				LocalDateTime.now());
		EnterIdStatus enterIdStatus = this.enterService.save_returnId(enterModel);
		
		this.appLogger.successed("入室情報生成の成功: enterId: " + enterIdStatus.getId());
		return enterIdStatus;
	}
	
	/**
	 * 部屋生成コメントの追加
	 * @param loginModel サインインモデル
	 * @param room_id    ルームID
	 */
	private void setComment_createroom(LoginModel loginModel, RoomIdStatus room_id) {
		this.appLogger.start("部屋生成コメント追加...");
		
		CommentModel commentModel = new CommentModel(
				new ChatCommentWord("部屋が作られました。"),
				room_id,
				new UserIdStatus(loginModel.getUser_id()),
				LocalDateTime.now());
		this.commentService.save(commentModel);

		this.appLogger.successed("部屋の生成コメントを追加しました。");
	}
	
	/**
	 * ルーム作成画面設定
	 * @param model
	 */
	private void setCreateroom_form(Model model) {
		// ルーム作成の設定
		model.addAttribute(WebConsts.BIND_TITLE, CreateRoomFormController.CREATE_ROOM_FORM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  CreateRoomFormController.CREATE_ROOM_FORM_MESSAGE);
	}
}
