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
import com.example.demo.app.service.CommentService;
import com.example.demo.app.service.EnterService;
import com.example.demo.app.service.LoginService;
import com.example.demo.app.service.RoomService;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;

/**
 * ---------------------------------------------------------------------------
 * 【入室実行コントローラ】
 * ---------------------------------------------------------------------------
 * 
 */
@Controller
@RequestMapping("/inroom")
public class EnterController {

	/**
	 * サービス
	 */
	private RoomService roomService;
	private CommentService commentService;
	private LoginService loginService;
	private EnterService enterService;
	
	/**
	 * コンストラクタ
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public EnterController(
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
	 * 入室受信
	 * @param userEnterForm: 入室フォーム
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
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
		int room_id = userEnterForm.getRoom_id();
		int login_id = userEnterForm.getLogin_id();
		LoginModel loginModel =  this.loginService.select(new LoginIdStatus(login_id));
		if(this.enterService.isSelect_byUserId(loginModel.getUser_id())) {
			// 既に入室している
			RoomModel roomModel = this.roomService.select(room_id);
			this.enterService.update_byUserId(room_id, roomModel.getUser_id(), roomModel.getMax_roomsum(), loginModel.getUser_id());	
			int out_enterId = this.enterService.selectId_byUserId(loginModel.getUser_id());
			redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, out_enterId);
		}else {
			// 入室情報なし
			// 入室情報登録
			this.setEnter(userEnterForm, model, redirectAttributes);
		}
		
		// ログイン情報のルーム番号更新
		this.loginService.updateRoomId_byId(
				new RoomIdStatus(room_id), 
				new LoginIdStatus(login_id));
		
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * 入室チェック
	 * @param userEnterForm: 入室フォーム
	 * @param redirectAttributes: リダイレクト
	 * @return true: 入室OK, false: 入室NG
	 */
	private boolean isEnter(
			UserEnterForm userEnterForm,
			RedirectAttributes redirectAttributes) {
		// 入室チェック
		if(userEnterForm.getLogin_id() == 0) {
			// ログインしてない
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "ログインしてください。");
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, 0);
			return false;
		}
		
		int room_id = userEnterForm.getRoom_id();
		int login_id = userEnterForm.getLogin_id();
		if((userEnterForm.getCount_sum() + 1) > userEnterForm.getMax_sum()) {
			// 既に満室
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "既に満室でした。");
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
			return false;
		}
		if(!this.roomService.isSelect_byId(room_id)) {
			// ルームがない
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "部屋が既に閉鎖された可能性があります。");
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
			return false;
		}
		
		// 入室OK
		return true;
	}
	
	/**
	 * 入室設定
	 * @param userEnterForm: 入室フォーム
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 */
	private void setEnter(
			UserEnterForm userEnterForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// 入室処理
		int room_id = userEnterForm.getRoom_id();
		int login_id = userEnterForm.getLogin_id();
		
		// 情報取得
		RoomModel roomModel = this.roomService.select(room_id);
		LoginModel loginModel = this.loginService.select(new LoginIdStatus(login_id));
		
		EnterModel enterModel = new EnterModel(
				new EnterIdStatus(0),
				new RoomIdStatus(room_id),
				new UserIdStatus(loginModel.getUser_id()),
				new UserIdStatus(roomModel.getUser_id()),
				new RoomMaxNumber(roomModel.getMax_roomsum()),
				LocalDateTime.now());
		int enter_id = this.enterService.save_returnId(enterModel);
		
		// 入室通知
		CommentModel commentModel = new CommentModel(
				new CommentIdStatus(0),
				new ChatCommentWord("入室されました。"),
				new RoomIdStatus(room_id),
				new UserIdStatus(loginModel.getUser_id()),
				LocalDateTime.now());
		this.commentService.save(commentModel);
		
		redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, enter_id);
	}
}
