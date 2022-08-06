package com.example.demo.app.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.exception.DatabaseUpdateException;
import com.example.demo.app.exception.NotFoundException;
import com.example.demo.app.form.RoomOutForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.session.SessionLoginId;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 部屋閉鎖コントローラー
 * @author nanai
 *
 */
@Controller
@RequestMapping("/closeroom")
public class RoomCloseController implements SuperChatController {

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
	 * セッションクラス
	 */
	@Autowired
	private SessionLoginId sessionLoginId;
	
	/**
	 * コンストラクタ
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public RoomCloseController(
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
	 * 【部屋閉鎖受信】
	 * @param roomOutForm        ログアウトフォーム
	 * @param model              モデル
	 * @param redirectAttributes リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping
	public String index(
			RoomOutForm roomOutForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("部屋閉鎖受信...");
		
		EnterIdStatus enter_id   = null;
		EnterModel    enterModel = null;
		UserIdStatus  user_id    = null;
		LoginIdStatus login_id   = null;
		RoomIdStatus  room_id    = null;
		
		// 情報取得
		try {
			enter_id   = new EnterIdStatus(roomOutForm.getEnter_id());
			enterModel = this.enterService.select(enter_id);
			user_id    = new UserIdStatus(enterModel.getUser_id());
			room_id    = new RoomIdStatus(enterModel.getRoom_id());
			login_id   = this.loginService.selectId_byUserId(user_id);
		} catch(NotFoundException ex) {
			// 情報取得できなかった
			this.appLogger.info("throw: " + ex);
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ログイン情報のルームIDの初期化
		this.initRoomId_byRoomId(room_id);
		
		// コメント情報の削除(ルームID)
		this.deleteComment_byRoomId(room_id);
		
		// 自身の入室情報の削除
		this.deleteEnterInfo(enter_id);
		
		// ルーム情報の削除
		this.deleteRoomInfo(room_id);
		
		// リダイレクト設定
		this.setRedirect(login_id, redirectAttributes);
		
		this.appLogger.successed("部屋閉鎖成功");
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * ログイン情報のルームID初期化
	 * @param room_id
	 */
	private void initRoomId_byRoomId(RoomIdStatus room_id) {
		this.appLogger.start("ログイン情報のルームID初期化...");
		
		try {
			this.loginService.updateRoomId_byRoomId(
					room_id, 
					new RoomIdStatus(0));	
		} catch(DatabaseUpdateException ex) {
			// 更新不可
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("ログイン情報のルームID初期化成功: roomId: " + room_id.getId());
	}
	
	/**
	 * コメント情報削除
	 * @param room_id
	 */
	private void deleteComment_byRoomId(RoomIdStatus room_id) {
		this.appLogger.start("コメント情報削除...");
		
		try {
			this.commentService.delete_byRoomId(room_id);
		} catch(NotFoundException ex) {
			// 削除不可
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("コメント情報削除成功: roomId: " + room_id.getId());			
	}
	
	/**
	 * 自身の入室情報の削除
	 * @param enter_id
	 */
	private void deleteEnterInfo(EnterIdStatus enter_id) {
		this.appLogger.start("自身の入室情報の削除...");
		
		try {
			this.enterService.delete(enter_id);
		} catch(NotFoundException ex) {
			// 削除不可
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("自身の入室情報の削除成功: enterId: " + enter_id.getId());
	}
	
	/**
	 * ルーム情報の削除
	 * @param room_id
	 */
	private void deleteRoomInfo(RoomIdStatus room_id) {
		this.appLogger.start("ルーム情報の削除...");
		
		try {
			this.roomService.delete(room_id);
		} catch(NotFoundException ex) {
			// 削除不可
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("ルーム情報の削除成功: roomId: " + room_id.getId());
	}
	
	/**
	 * リダイレクト設定
	 * @param login_id
	 * @param redirectAttributes
	 */
	private void setRedirect(LoginIdStatus login_id, RedirectAttributes redirectAttributes) {
		// ログインIDのリダイレクト
		String encryptNumber = CommonEncript.encrypt(login_id.getId());
		redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_LOGIN_ID, encryptNumber);
		this.sessionLoginId.setLoginId(encryptNumber);
	}
}
