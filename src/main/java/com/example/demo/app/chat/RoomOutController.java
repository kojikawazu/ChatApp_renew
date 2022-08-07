package com.example.demo.app.chat;

import java.time.LocalDateTime;

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
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.exception.DatabaseUpdateException;
import com.example.demo.app.exception.NotFoundException;
import com.example.demo.app.form.RoomOutForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.session.SessionLoginId;
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
	
	/**
	 * セッションクラス
	 */
	@Autowired
	private SessionLoginId sessionLoginId;
	
	/** 退室された通知 */
	private final String NOTICE_LEFT_THE_ROOM = "退室されました。";
	
	private final String NOTICE_LEAVE_LEFT_THE_ROOM = "一定時間を過ぎましたので、退室しました。";
	
	/** 退室OK */
	private static final boolean ROOMOUT_OK = true;
	
	/** 退室NG */
	private static final boolean ROOMOUT_NG = false;
	
	
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
	 * 【ログアウト受信(POST)】
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
		this.appLogger.start("[POST]退室受信...");
		
		LoginIdStatus login_id = null;
		EnterIdStatus enter_id = null;
		EnterModel enterModel  = null;
		UserIdStatus user_id   = null;
		RoomIdStatus room_id   = null;
		LoginModel loginModel  = null;
		
		// 情報取得
		try {
			login_id     = new LoginIdStatus(roomOutForm.getLogin_id());
			enter_id     = new EnterIdStatus(roomOutForm.getEnter_id());
			enterModel   = this.enterService.select(enter_id);
			user_id      = new UserIdStatus(enterModel.getUser_id());
			room_id      = new RoomIdStatus(enterModel.getRoom_id());
			loginModel   = this.loginService.select(login_id);
		} catch(NotFoundException ex) {
			// 情報取得なし
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			this.setRedirect(login_id, redirectAttributes);
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// 入室情報の削除
		this.deleteEnterInfo(enter_id);
		
		// 退室処理チェック
		if( !this.isRoomOut(loginModel) ) {
			this.setRedirect(login_id, redirectAttributes);
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ---------------------------------------------------------------------------------
		// まだ入室中の場合
		
		// ログイン情報のルームIDの初期化
		this.initRoomId_byUserId(user_id);
		
		// 退室情報の通知
		this.setComment(user_id, room_id);
		
		// リダイレクト設定
		this.setRedirect(login_id, redirectAttributes);

		this.appLogger.successed("[POST]退室成功");
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * 【ログアウト受信(GET)】
	 * @param e_enterId          入室ID
	 * @param e_loginId          ログインID
	 * @param model              モデル
	 * @param redirectAttributes リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@GetMapping
	public String indexGET(
			@RequestParam(value = WebConsts.BIND_ENCRYPT_ENTER_ID, 
				required = false, 
				defaultValue = "wfssM4JI4nk=") String e_enterId,
			@RequestParam(value = WebConsts.BIND_ENCRYPT_LOGIN_ID, 
				required = false, 
				defaultValue = "wfssM4JI4nk=") String e_loginId,
			Model model,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("[GET]退室受信...");
		
		int enterIdInt = Integer.parseInt(CommonEncript.decrypt(e_enterId));
		this.appLogger.info("復号化... enterId: " + enterIdInt);
		EnterIdStatus enter_id = new EnterIdStatus(enterIdInt);
		
		int loginIdInt = Integer.parseInt(CommonEncript.decrypt(e_loginId));
		this.appLogger.info("復号化... loginId: " + loginIdInt);
		LoginIdStatus login_id = new LoginIdStatus(loginIdInt);
		
		EnterModel enterModel  = null;
		UserIdStatus user_id   = null;
		RoomIdStatus room_id   = null;
		LoginModel loginModel  = null;
		
		// 情報取得
		try {
			enterModel   = this.enterService.select(enter_id);
			user_id      = new UserIdStatus(enterModel.getUser_id());
			room_id      = new RoomIdStatus(enterModel.getRoom_id());
			loginModel   = this.loginService.select(login_id);
		} catch(NotFoundException ex) {
			// 情報取得なし
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			this.setRedirect(login_id, redirectAttributes);
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// 入室情報の削除
		this.deleteEnterInfo(enter_id);
		
		// 退室処理チェック
		if( !this.isRoomOut(loginModel) ) {
			this.setRedirect(login_id, redirectAttributes);
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ---------------------------------------------------------------------------------
		// まだ入室中の場合
		
		// ログイン情報のルームIDの初期化
		this.initRoomId_byUserId(user_id);
		
		// 退室情報の通知
		this.setComment(user_id, room_id);
		
		// リダイレクト設定
		this.setRedirect(login_id, redirectAttributes);
		
		// エラー内容登録
		redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, NOTICE_LEAVE_LEFT_THE_ROOM);

		this.appLogger.successed("[GET]退室成功");
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * 退室できるかチェック
	 * @param loginModel サインインモデル
	 * @return true 退室可能 false 退室不可
	 */
	private boolean isRoomOut(LoginModel loginModel) {
		if(loginModel.getRoom_id() == WebConsts.ZERO_NUMBER) {
			// 既に強制退室されている
			this.appLogger.info("既に退室していた： loginId: " + loginModel.getId());
			return ROOMOUT_NG;
		}
		return ROOMOUT_OK;
	}
	
	/**
	 * ログイン情報のルームIDの初期化
	 * @param user_id ユーザーID
	 */
	private void initRoomId_byUserId(UserIdStatus user_id) {
		this.appLogger.start("ログイン情報のルームIDの初期化...");
		
		try {
			this.loginService.updateRoomId_byUserId(
					new RoomIdStatus(0), 
					user_id);
		} catch(DatabaseUpdateException ex) {
			// 更新不可
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("ログイン情報のルームIDの初期化成功: userId: " + user_id.getId());
	}
	
	/**
	 * 入室情報の削除
	 * @param enter_id 入室ID
	 */
	private void deleteEnterInfo(EnterIdStatus enter_id) {
		this.appLogger.start("入室情報の削除...");
		
		try {
			this.enterService.delete(enter_id);
		} catch(NotFoundException ex) {
			// 削除不可
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("入室情報の削除成功: enterId: " + enter_id.getId());
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
	 * リダイレクト設定
	 * @param login_id
	 * @param redirectAttributes
	 */
	private void setRedirect(LoginIdStatus login_id, RedirectAttributes redirectAttributes) {
		// ログインIDの設定
		String encryptNumber = CommonEncript.encrypt(login_id.getId());
		redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_LOGIN_ID, encryptNumber);
		this.sessionLoginId.setLoginId(encryptNumber);
	}
}
