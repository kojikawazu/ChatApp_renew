package com.example.demo.app.chat;

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
import com.example.demo.app.form.RoomOutForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;

/**
 * ログアウトコントローラー
 * @author nanai
 *
 */
@Controller
@RequestMapping("/outroom")
public class RoomOutController implements SuperChatController {

	/**
	 * サービス 
	 */
	private CommentService commentService;
	private LoginService   loginService;
	private EnterService   enterService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** 退室された通知 */
	private final String NOTICE_LEFT_THE_ROOM = "退室されました。";
	
	/**
	 * コンストラクタ
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public RoomOutController(
			CommentService commentService,
			LoginService   loginService,
			EnterService   enterService) {
		this.commentService = commentService;
		this.loginService   = loginService;
		this.enterService   = enterService;
	}
	
	/**
	 * 【ログアウト受信】
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
		this.appLogger.start("退室受信...");
		
		// 情報取得
		EnterIdStatus  enter_id   = new EnterIdStatus(roomOutForm.getEnter_id());
		EnterModel     enterModel = this.enterService.select(enter_id);
		UserIdStatus   user_id    = new UserIdStatus(enterModel.getUser_id());
		RoomIdStatus   room_id    = new RoomIdStatus(enterModel.getRoom_id());
		LoginIdStatus  login_id   = this.loginService.selectId_byUserId(user_id);
		
		// 退室情報の通知
		this.setComment(user_id, room_id);
		
		// ログイン情報のルームIDの初期化
		this.initRoomId_byUserId(user_id);
		
		// 入室情報の削除
		this.deleteEnterInfo(enter_id);
		
		// リダイレクト設定
		String encryptNumber = CommonEncript.encrypt(String.valueOf(login_id.getId()));
		redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, encryptNumber);

		this.appLogger.successed("退室成功");
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * 【退室コメント通知】
	 * @param user_id ユーザID
	 * @param room_id ルームID
	 */
	private void setComment(UserIdStatus user_id, RoomIdStatus room_id) {
		this.appLogger.start("退室コメント通知...");
		
		CommentModel commentModel = new CommentModel(
				new ChatCommentWord(NOTICE_LEFT_THE_ROOM),
				room_id,
				user_id,
				LocalDateTime.now());
		this.commentService.save(commentModel);
		
		this.appLogger.successed("退室コメント通知成功");
	}
	
	/**
	 * ログイン情報のルームIDの初期化
	 * @param user_id ユーザーID
	 */
	private void initRoomId_byUserId(UserIdStatus user_id) {
		this.appLogger.start("ログイン情報のルームIDの初期化...");
		
		this.loginService.updateRoomId_byUserId(
				new RoomIdStatus(0), 
				user_id);
		
		this.appLogger.successed("ログイン情報のルームIDの初期化成功: userId: " + user_id.getId());
	}
	
	/**
	 * 入室情報の削除
	 * @param enter_id 入室ID
	 */
	private void deleteEnterInfo(EnterIdStatus enter_id) {
		this.appLogger.start("入室情報の削除...");
		
		this.enterService.delete(enter_id);
		
		this.appLogger.successed("入室情報の削除成功: enterId: " + enter_id.getId());
		
	}
}
