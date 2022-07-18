package com.example.demo.app.chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.CommentModelEx;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.LoginModelEx;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.RoomModelEx;
import com.example.demo.app.entity.UserModel;
import com.example.demo.app.form.RoomLeaveForm;
import com.example.demo.app.form.RoomOutForm;
import com.example.demo.app.form.UserSpeechForm;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.number.RoomEnterCntNumber;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.service.UserService;
import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;
import com.example.demo.common.word.NameWord;

/**
 * 【チャットコントローラー】
 * @author nanai
 */
@Controller
@RequestMapping("/chat")
public class ChatController {
	
	/**
	 * サービス 
	 */
	private UserService    userService;
	private RoomService    roomService;
	private CommentService commentService;
	private LoginService   loginService;
	private EnterService   enterService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** 部屋閉鎖された通知 */
	private final String NOTICE_ROOM_CLOSE = "部屋が閉鎖されました。";
	
	/** 強制退室通知 */
	private final String NOTICE_FORCE_LEFT_THE_ROOM = "強制退室されました。";
	
	/**
	 * コンストラクタ
	 * @param userService
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public ChatController(
			UserService    userService,
			RoomService    roomService,
			CommentService commentService,
			LoginService   loginService,
			EnterService   enterService) {
		this.userService    = userService;
		this.roomService    = roomService;
		this.commentService = commentService;
		this.loginService   = loginService;
		this.enterService   = enterService;
	}
	
	/**
	 * 【チャットホーム】
	 * @param enter_id            入室ID
	 * @param model               モデル
	 * @param roomLeaveForm       フォーム 
	 * @param redirectAttributes  リダイレクト
	 * @return Webパス (redirect:/room or chat/room)
	 */
	@GetMapping
	public String index(
			@RequestParam(value = "enter_id", required = false, defaultValue = "0") int enter_id,
			Model model,
			RoomLeaveForm roomLeaveForm,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("チャットルーム受信...");
		EnterIdStatus enterIdStatus = new EnterIdStatus(enter_id);
		
		// エラーチェック
		if( !this.enterService.isSelect_byId(enterIdStatus) ) {
			// [ERROR]
			// 入室IDがない場合、ルーム画面へリダイレクト
			this.appLogger.error("入室IDなし...ルーム画面へ");
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		EnterModel enterModel = this.enterService.select(enterIdStatus);
		if( !this.isEnterCheck(enterModel, redirectAttributes) ) {
			// [ERROR]
			// 入室NGな場合、ルーム画面へリダイレクト
			this.appLogger.error("入室NG...ルーム画面へ");
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ビュー画面へ設定
		this.setIndex(enterModel, model, enter_id);
		
		this.appLogger.successed("チャットルーム成功");
		return WebConsts.URL_CHAT_INDEX;
	}
	
	// -------------------------------------------------------------------------------------------------------
	
	/**
	 * [入室チェック]
	 * @param enterModel           入室モデル
	 * @param redirectAttributes   リダイレクト
	 * @return true   入室OK false 入室NG
	 */
	private boolean isEnterCheck(
			EnterModel enterModel,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("入室チェック...");
		UserIdStatus  user_id  = new UserIdStatus(enterModel.getUser_id());
		
		// エラーチェック
		RoomIdStatus room_id_check1 = new RoomIdStatus(enterModel.getRoom_id());
		if( !this.roomService.isSelect_byId(room_id_check1) ) {
			// [ERROR]
			this.appLogger.error("既に閉鎖: roomId: " + enterModel.getRoom_id());
			
			// 入室IDを削除
			EnterIdStatus enter_id = new EnterIdStatus(enterModel.getId());
			this.enterService.delete(enter_id);
			
			// ユーザIDからログインIDを取得
			LoginIdStatus login_id = this.loginService.selectId_byUserId(user_id);
			
			// ログインIDをリダイレクト
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, NOTICE_ROOM_CLOSE);
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id.getId());
			
			return false;
		}
		
		RoomIdStatus room_id_check2 = this.loginService.selectRoomId_byUserId(user_id);
		if( room_id_check2.getId() == WebConsts.ERROR_DB_STATUS ) {
			// [ERROR]
			this.appLogger.error("強制退室された: roomId: " + room_id_check2.getId());
			this.appLogger.error("           : userId: " + user_id.getId());
			
			// 入室IDを削除
			EnterIdStatus enter_id = new EnterIdStatus(enterModel.getId());
			this.enterService.delete(enter_id);
			
			// ユーザIDからログインIDを取得
			LoginIdStatus login_id = this.loginService.selectId_byUserId(user_id);
			
			// ログインIDをリダイレクト
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, NOTICE_FORCE_LEFT_THE_ROOM);
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id.getId());
			return false;
		}
		
		this.appLogger.successed("入室OK");
		return true;
	}
	
	/**
	 * 【チャットルーム設定】
	 * @param enterModel 入室モデル
	 * @param model      モデル
	 * @param enter_id   入室ID
	 */
	private void setIndex(EnterModel enterModel, Model model, int enter_id) {
		this.appLogger.start("チャットルーム設定...");
		
		// ホストユーザチェック
		LoginModel lnModel = this.loginService.select_byUserId(new UserIdStatus(enterModel.getManager_id()));
		if(lnModel.getRoom_id() != enterModel.getRoom_id()) {
			// 既にホストが変更されている場合
			// ホストユーザIDを変更
			enterModel = this.updateEnterModel(enterModel);
		}
		
		// ルーム情報を取得
		RoomIdStatus         enterRoomId        = new RoomIdStatus(enterModel.getRoom_id());
		UserIdStatus         enterUserId        = new UserIdStatus(enterModel.getUser_id());
		UserIdStatus         enterManagerId     = new UserIdStatus(enterModel.getManager_id());
		
		RoomModelEx          roomModelEx        = this.roomService.select_plusUserName_EnterCnt(enterRoomId);
		UserModel            userModel          = this.userService.select(enterUserId);
		UserModel            hostModel          = this.userService.select(enterManagerId);
		RoomIdStatus         room_id            = this.loginService.selectRoomId_byUserId(enterUserId);
		List<CommentModelEx> commentModelExList = this.commentService.select_plusUserName_byRoomId(enterRoomId);
		List<LoginModelEx>   loginModelExList   = this.loginService.selectList_plusUserName_byRoomId(room_id);
		
		// データをバインド
		model.addAttribute(WebConsts.BIND_TITLE,            "チャットルームへようこそ");
		model.addAttribute(WebConsts.BIND_CONT,             "自由に遊んでください。");
		model.addAttribute(WebConsts.BIND_ENTER_ID,          enter_id);
		model.addAttribute(WebConsts.BIND_ROOMMODEL,         roomModelEx);
		model.addAttribute(WebConsts.BIND_USERMODEL,         userModel);
		model.addAttribute(WebConsts.BIND_HOSTMODEL,         hostModel);
		model.addAttribute(WebConsts.BIND_COMMENTMODEL_LIST, commentModelExList);
		model.addAttribute(WebConsts.BIND_LOGINMODEL_LIST,   loginModelExList);
		
		this.appLogger.successed("チャットルーム設定完了");
	}
	
	
	private EnterModel updateEnterModel(EnterModel enterModel) {
		this.appLogger.start("ホストユーザーID変更...");
		
		this.enterService.updateManagerId_byId(
				new UserIdStatus(enterModel.getUser_id()), 
				new EnterIdStatus(enterModel.getId()));
		this.roomService.updateUserId_byUserId(
				new UserIdStatus(enterModel.getManager_id()),
				new UserIdStatus(enterModel.getUser_id()));
		
		// 入室モデルチェンジ
		EnterModel newEnterModel = new EnterModel(
				new EnterIdStatus(enterModel.getId()),
				new RoomIdStatus(enterModel.getRoom_id()),
				new UserIdStatus(enterModel.getUser_id()),
				new UserIdStatus(enterModel.getUser_id()),
				enterModel.getCreated());
		
		this.appLogger.successed("ホストユーザーID変更完了");
		return newEnterModel;
	}
}
