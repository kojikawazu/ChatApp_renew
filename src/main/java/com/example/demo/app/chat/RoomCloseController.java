package com.example.demo.app.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.form.RoomOutForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.service.UserService;
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
		
		// 情報取得
		EnterIdStatus enter_id   = new EnterIdStatus(roomOutForm.getEnter_id());
		EnterModel    enterModel = this.enterService.select(enter_id);
		UserIdStatus  user_id    = new UserIdStatus(enterModel.getUser_id());
		LoginIdStatus login_id   = this.loginService.selectId_byUserId(user_id);
		RoomIdStatus  room_id    = new RoomIdStatus(enterModel.getRoom_id());
		
		// ログイン情報のルームIDの初期化
		this.initRoomId_byRoomId(room_id);
		
		// コメント情報の削除(ルームID)
		this.deleteComment_byRoomId(room_id);
		
		// 自身の入室情報の削除
		this.deleteEnterInfo(enter_id);
		
		// ルーム情報の削除
		this.deleteRoomInfo(room_id);
		
		// リダイレクト設定
		String encryptNumber = CommonEncript.encrypt(login_id.getId());
		redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_LOGIN_ID, encryptNumber);
		
		this.appLogger.successed("部屋閉鎖成功");
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * ログイン情報のルームID初期化
	 * @param room_id
	 */
	private void initRoomId_byRoomId(RoomIdStatus room_id) {
		this.appLogger.start("ログイン情報のルームID初期化...");
		
		this.loginService.updateRoomId_byRoomId(
				room_id, 
				new RoomIdStatus(0));
		
		this.appLogger.successed("ログイン情報のルームID初期化成功: roomId: " + room_id.getId());
	}
	
	/**
	 * コメント情報削除
	 * @param room_id
	 */
	private void deleteComment_byRoomId(RoomIdStatus room_id) {
		this.appLogger.start("コメント情報削除...");
		
		this.commentService.delete_byRoomId(room_id);

		this.appLogger.successed("コメント情報削除成功: roomId: " + room_id.getId());			
	}
	
	/**
	 * 自身の入室情報の削除
	 * @param enter_id
	 */
	private void deleteEnterInfo(EnterIdStatus enter_id) {
		this.appLogger.start("自身の入室情報の削除...");
		
		this.enterService.delete(enter_id);
		
		this.appLogger.successed("自身の入室情報の削除成功: enterId: " + enter_id.getId());
	}
	
	/**
	 * ルーム情報の削除
	 * @param room_id
	 */
	private void deleteRoomInfo(RoomIdStatus room_id) {
		this.appLogger.start("ルーム情報の削除...");
		
		this.roomService.delete(room_id);
		
		this.appLogger.successed("ルーム情報の削除成功: roomId: " + room_id.getId());
	}
}
