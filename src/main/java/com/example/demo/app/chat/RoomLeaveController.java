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
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.UserModel;
import com.example.demo.app.exception.DatabaseUpdateException;
import com.example.demo.app.exception.NotFoundException;
import com.example.demo.app.form.RoomLeaveForm;
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
import com.example.demo.common.word.ChatCommentWord;

/**
 * 強制退室コントローラー
 * @author nanai
 *
 */
@Controller
@RequestMapping("/userclose")
public class RoomLeaveController implements SuperChatController {

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
	
	/** 強制退室通知 */
	private final String NOTICE_FORCE_LEFT_THE_ROOM = "さんを強制退室させました。";
	
	/** 強制退室OK */
	private static final boolean LEAVE_OK = true;
	
	/** 強制退室NG */
	private static final boolean LEAVE_NG = false;
	
	/**
	 * コンストラクタ
	 * @param userService
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public RoomLeaveController(
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
	 * 【強制退室】
	 * @param roomLeaveForm      強制退室フォーム
	 * @param model              モデル
	 * @param redirectAttributes リダイレクト
	 * @return Webパス: (redirect:/chat)
	 */
	@PostMapping
	public String index(
			RoomLeaveForm roomLeaveForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("強制退室受信...");
		
		// 強制退室チェック
		if( !this.isLeave(roomLeaveForm) ) {
			// [ERROR]
			// 強制退室エラー
			// 何もせずリダイレクト
			this.setRedirect(roomLeaveForm, redirectAttributes);
			return WebConsts.URL_REDIRECT_CHAT_INDEX;
		}
		
		EnterIdStatus enterId = null;
		RoomIdStatus roomId   = null;
		EnterModel enterModel = null;
		LoginModel loginModel = null;  
		try {
			// 情報取得
			enterId               = new EnterIdStatus(roomLeaveForm.getEnter_id());
			enterModel            = this.enterService.select(enterId);
			roomId                = new RoomIdStatus(enterModel.getRoom_id());
			loginModel            = this.loginService.select(new LoginIdStatus(roomLeaveForm.getIn_id()));
		} catch(NotFoundException ex) {
			// 情報取得なし
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			this.setRedirect(roomLeaveForm, redirectAttributes);
			return WebConsts.URL_REDIRECT_CHAT_INDEX;
		}
		
		// ログイン情報のルーム番号の初期化
		this.initRoomId_byLoginId(roomLeaveForm);
		
		// 強制退室通知
		this.noticeLeave(roomLeaveForm, enterModel);
		
		// 日付の更新
		this.updateTimed(enterId, roomId);
		
		// チャットリダイレクト
		this.setRedirect(roomLeaveForm, redirectAttributes);
		
		this.appLogger.successed("強制退室成功");
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * 強制退室チェック
	 * @param roomLeaveForm 強制退室フォーム
	 * @return true 強制退室可能 false 強制退室不可
	 */
	private boolean isLeave(RoomLeaveForm roomLeaveForm) {
		
		// 強制退室対象がいるかチェック
		if(roomLeaveForm.getIn_id() == WebConsts.ZERO_NUMBER) {
			// [ERROR]
			// 強制退室対象がいない
			this.appLogger.error("強制退室失敗");
			return LEAVE_NG;
		}
		
		try {
			// 情報取得
			LoginIdStatus loginId = new LoginIdStatus(roomLeaveForm.getIn_id());
			LoginModel loginModel = this.loginService.select(loginId);
			
			// 初期化済か確認
			if(loginModel.getRoom_id() == WebConsts.ZERO_NUMBER) {
				// [ERROR]
				// 初期化済
				this.appLogger.error("退室済だった");
				return LEAVE_NG;
			}
		} catch(NotFoundException ex) {
			// 情報なし
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return LEAVE_NG;
		}
		
		return LEAVE_OK;
	}
	
	/**
	 * ログインIDによるルームIDの初期化
	 * @param roomLeaveForm
	 */
	private void initRoomId_byLoginId(RoomLeaveForm roomLeaveForm) {
		this.appLogger.start("ログインIDによるルームIDの初期化...");
		
		try {
			this.loginService.updateRoomId_byId(
				new RoomIdStatus(0), 
				new LoginIdStatus(roomLeaveForm.getIn_id()));
		} catch(DatabaseUpdateException ex) {
			// 更新不可
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("ログインIDによるルームIDの初期化成功: roomId: " + roomLeaveForm.getIn_id());
	}
	
	/**
	 * 入室モデルの削除
	 * @param roomLeaveForm
	 */
	private void deleteEnterModel(LoginModel loginModel) {
		try {
			
			UserIdStatus userId = new UserIdStatus(loginModel.getUser_id());
			this.enterService.delete_byUserId(userId);
			
		} catch(NotFoundException ex) {
			// 削除不可
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return ;
		}
	}
	
	/**
	 * 強制退室通知
	 * @param roomLeaveForm
	 */
	private void noticeLeave(RoomLeaveForm roomLeaveForm, EnterModel enterModel) {
		this.appLogger.start("強制退室通知...");
		
		try {
			// 情報取得
			LoginModel loginModel = this.loginService.select(new LoginIdStatus(roomLeaveForm.getIn_id()));
			UserModel  userModel  = this.userService.select(new UserIdStatus(loginModel.getUser_id()));
			
			// 強制退室通知
			CommentModel commentModel = new CommentModel(
					new ChatCommentWord(userModel.getName() +  NOTICE_FORCE_LEFT_THE_ROOM),
					new RoomIdStatus(enterModel.getRoom_id()),
					new UserIdStatus(enterModel.getUser_id()),
					LocalDateTime.now());
			this.commentService.save(commentModel);
			
		} catch(NotFoundException ex) {
			// 情報取得なし
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("強制退室通知成功");
	}
	
	/**
	 * 日付の更新
	 * @param enter_id 入室ID
	 * @param room_id  ルームID
	 */
	private void updateTimed(EnterIdStatus enter_id, RoomIdStatus room_id) {
		this.appLogger.start("更新日付の更新処理...");
		LocalDateTime now = LocalDateTime.now();
		
		try {
			// 入室IDの更新
			this.enterService.updateUpdated_byId(now, enter_id);
			
			// ルームIDの更新
			this.roomService.updateUpdated_byId(now, room_id);
						
		} catch(DatabaseUpdateException ex) {
			// 更新不可
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("更新日付の更新処理成功");
	}
	
	/**
	 * リダイレクト設定
	 * @param roomLeaveForm
	 * @param redirectAttributes
	 */
	private void setRedirect(RoomLeaveForm roomLeaveForm, RedirectAttributes redirectAttributes) {
		// 入室IDのリダイレクト
		String encryptNumber = CommonEncript.encrypt(roomLeaveForm.getEnter_id());
		redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_ENTER_ID, encryptNumber);
	}
}
