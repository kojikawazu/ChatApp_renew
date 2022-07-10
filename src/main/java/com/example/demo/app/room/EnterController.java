package com.example.demo.app.room;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.form.UserEnterForm;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;

/**
 * ---------------------------------------------------------------------------
 * 【入室実行コントローラ】
 * @author nanai
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/inroom")
public class EnterController implements SuperRoomController {

	/**
	 * メッセージ
	 */
	/** ログイン促し */
	public static String ERROR_MESSAGE_NO_LOGIN = "ログインしてください。";
	
	/** 既に満室 */
	public static String ERROR_MESSAGE_MAX_ENTER_ROOM = "既に満室でした。";
	
	/** 閉鎖された */
	public static String ERROR_MESSAGE_CLOSUER = "部屋が既に閉鎖された可能性があります。";
	
	/** ログインID(初期化番号) */
	public static int NO_LOGIN_NUMBER = 0;
	
	/**
	 * サービス
	 */
	private RoomService    roomService;
	private CommentService commentService;
	private LoginService   loginService;
	private EnterService   enterService;
	
	/**
	 * コンストラクタ
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public EnterController(
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
	 * 入室受信
	 * @param userEnterForm 入室フォーム
	 * @param model モデル
	 * @param redirectAttributes リダイレクト
	 * @return (redirect:/room, redirect:/chat)
	 */
	@PostMapping
	public String index(
			UserEnterForm userEnterForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// 入室
		
		// 入室チェック
		if( !isEnter(userEnterForm, redirectAttributes) ) {
			// 何もしない
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// 入室情報登録
		RoomIdStatus  room_id    =  new RoomIdStatus(userEnterForm.getRoom_id());
		LoginIdStatus login_id   =  new LoginIdStatus(userEnterForm.getLogin_id());
		LoginModel    loginModel =  this.loginService.select(login_id);
		UserIdStatus  user_id    =  new UserIdStatus(loginModel.getUser_id());
		
		// 入室チェック
		if(this.enterService.isSelect_byUserId(user_id)) {
			// 既に入室している
			// 入室情報更新
			EnterIdStatus out_enterId = this.updateEnter(room_id, loginModel);
			redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, out_enterId.getId());
		}else {
			// 入室情報なし
			// 入室情報登録
			this.setEnter(userEnterForm, model, redirectAttributes);
		}
		
		// ログイン情報のルーム番号更新
		this.loginService.updateRoomId_byId(
				room_id, 
				login_id);
		
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * 入室チェック
	 * @param userEnterForm      入室フォーム
	 * @param redirectAttributes リダイレクト
	 * @return true 入室OK, false 入室NG
	 */
	private boolean isEnter(
			UserEnterForm userEnterForm,
			RedirectAttributes redirectAttributes) {
		
		// 入室チェック
		if(userEnterForm.getLogin_id() == 0) {
			// ログインしてない
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, ERROR_MESSAGE_NO_LOGIN);
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, NO_LOGIN_NUMBER);
			return WebConsts.CHECK_COMMON_NG;
		}
		// ログインしてる
		
		RoomIdStatus  room_id   = new RoomIdStatus(userEnterForm.getRoom_id());
		LoginIdStatus login_id  = new LoginIdStatus(userEnterForm.getLogin_id());
		
		// 満室チェック
		if((userEnterForm.getCount_sum() + 1) > userEnterForm.getMax_sum()) {
			// 既に満室
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, ERROR_MESSAGE_MAX_ENTER_ROOM);
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id.getId());
			return WebConsts.CHECK_COMMON_NG;
		}
		// 空きあり
		
		// ルームチェック
		if(!this.roomService.isSelect_byId(room_id)) {
			// ルームがない
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, ERROR_MESSAGE_CLOSUER);
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id.getId());
			return WebConsts.CHECK_COMMON_NG;
		}
		// ルームあり
		
		// 入室OK
		return WebConsts.CHECK_COMMON_OK;
	}
	
	/**
	 * 入室設定
	 * @param userEnterForm      入室フォーム
	 * @param model              モデル
	 * @param redirectAttributes リダイレクト
	 */
	private void setEnter(
			UserEnterForm userEnterForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// 入室処理
		
		// 情報取得
		RoomIdStatus  room_id     = new RoomIdStatus(userEnterForm.getRoom_id());
		LoginIdStatus login_id    = new LoginIdStatus(userEnterForm.getLogin_id());
		RoomModel     roomModel   = this.roomService.select(room_id);
		LoginModel    loginModel  = this.loginService.select(login_id);
		
		// 入室情報保存
		EnterModel enterModel = new EnterModel(
				room_id,
				new UserIdStatus(loginModel.getUser_id()),
				new UserIdStatus(roomModel.getUser_id()),
				new RoomMaxNumber(roomModel.getMax_roomsum()),
				LocalDateTime.now());
		EnterIdStatus enter_id = this.enterService.save_returnId(enterModel);
		
		// 入室通知保存
		CommentModel commentModel = new CommentModel(
				new ChatCommentWord("入室されました。"),
				room_id,
				new UserIdStatus(loginModel.getUser_id()),
				LocalDateTime.now());
		this.commentService.save(commentModel);
		
		redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, enter_id.getId());
	}
	
	
	/**
	 * 入室情報の更新
	 * @param room_id     ルームID
	 * @param loginModel  ログインモデル
	 * @return            入室ID
	 */
	private EnterIdStatus updateEnter(RoomIdStatus room_id, LoginModel loginModel) {
		// 入室情報更新
		RoomModel roomModel = this.roomService.select(room_id);
		this.enterService.update_byUserId(
				room_id, 
				new UserIdStatus(roomModel.getUser_id()), 
				new RoomMaxNumber(roomModel.getMax_roomsum()), 
				new UserIdStatus(loginModel.getUser_id()));
		
		// 入室IDを返す
		return this.enterService.selectId_byUserId(
				new UserIdStatus(loginModel.getUser_id()));
	}
}
