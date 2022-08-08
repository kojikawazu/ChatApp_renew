package com.example.demo.app.chat;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.config.WebFunctions;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.exception.DatabaseUpdateException;
import com.example.demo.app.exception.NotFoundException;
import com.example.demo.app.form.UserSpeechForm;
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;

/**
 * コメント情報受信コントローラー
 * @author nanai
 *
 */
@Controller
@RequestMapping("/speech")
public class RoomSpeechController implements SuperChatController {

	/**
	 * サービス 
	 */
	private CommentService commentService;
	private RoomService    roomService;
	private LoginService   loginService;
	private EnterService   enterService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/** チャット投稿OK */
	private static final boolean SPEECH_OK = true;
	
	/** チャット投稿NG */
	private static final boolean SPEECH_NG = false;
	
	/** 投稿タイマー時間 */
	private static final int SPEECH_TIMER = 30;
	
	/**
	 * コンストラクタ
	 * @param roomService     ルームサービス
	 * @param commentService  コメントサービス
	 * @param loginService    ログインサービス
	 * @param enterService    入室サービス
	 */
	@Autowired
	public RoomSpeechController(
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
	 * 【コメント情報受信】
	 * @param userSpeechForm      ユーザコメントフォーム
	 * @param model               モデル
	 * @param redirectAttributes  リダイレクト
	 * @return Webパス(redirect:/chat)
	 */
	@PostMapping
	public String index(
			UserSpeechForm userSpeechForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		this.appLogger.start("コメント受信...");
		
		// エラーチェック
		if( !this.isSpeech(userSpeechForm, model) ) {
			// コメント追加NGでリダイレクト
			this.setRedirect(userSpeechForm, redirectAttributes);
			return WebConsts.URL_REDIRECT_CHAT_INDEX;
		}
		
		// コメント情報追加
		this.setSpeech(userSpeechForm);
		
		// 時間更新
		this.updateTimed(userSpeechForm);
		
		// コメント追加後にリダイレクト
		this.setRedirect(userSpeechForm, redirectAttributes);
		
		this.appLogger.successed("コメント成功");
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * チャット投稿チェック
	 * @param userSpeechForm      スピーチフォーム
	 * @param model               モデル
	 * @param redirectAttributes  リダイレクトクラス
	 * @return true OK false NG
	 */
	private boolean isSpeech(UserSpeechForm userSpeechForm,
			Model model) {
		this.appLogger.start("チャット投稿チェック...");
		
		// コメント内容チェック
		if(userSpeechForm.getComment() == null || userSpeechForm.getComment().isBlank()) {
			// [ERROR]
			// コメント追加なしでリダイレクト
			this.appLogger.info("コメントなし");
			return SPEECH_NG;
		}
		
		// 部屋あるかチェック
		if( !this.roomService.isSelect_byId(new RoomIdStatus(userSpeechForm.getRoom_id())) ) {
			// [ERROR]
			// 部屋なしでリダイレクト
			this.appLogger.info("部屋なし");
			return SPEECH_NG;
		}
		
		// 更新日付チェック
		EnterIdStatus enterId = null;
		EnterModel enterModel = null;
		LoginModel loginModel = null;
		try {
			enterId    = new EnterIdStatus(userSpeechForm.getEnter_id());
			enterModel = this.enterService.select(enterId);
			loginModel = this.loginService.select_byUserId(new UserIdStatus(userSpeechForm.getUser_id()));
		} catch(NotFoundException ex) {
			// 情報取得なし
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return SPEECH_NG;
		}
		
		if(loginModel.getRoom_id() == WebConsts.ZERO_NUMBER) {
			// [ERROR]
			// 強制退室されていた
			this.appLogger.info("強制退室済");
			return SPEECH_NG;
		}
		
		// 時間が超過してないか確認
		if( !WebFunctions.checkDiffMinutes(enterModel.getUpdated(), SPEECH_TIMER) ) {
			// [ERROR]
			// 更新日付だいぶ経っている
			this.appLogger.info("更新日付切れ");
			return SPEECH_NG;
		}
		
		this.appLogger.successed("チャット投稿チェックOK");
		return SPEECH_OK;
	}
	
	/**
	 * 【コメント追加処理】
	 * @param userSpeechForm 投稿フォーム
	 */
	private void setSpeech(UserSpeechForm userSpeechForm) {
		this.appLogger.start("投稿情報の追加");
		
		CommentModel commentModel = new CommentModel(
				new ChatCommentWord(userSpeechForm.getComment()),
				new RoomIdStatus(userSpeechForm.getRoom_id()),
				new UserIdStatus(userSpeechForm.getUser_id()),
				LocalDateTime.now());
		this.commentService.save(commentModel);
		
		this.appLogger.successed("投稿情報の追加成功");
	}
	
	/**
	 * 時間更新
	 * @param userSpeechForm 投稿フォーム
	 */
	private void updateTimed(UserSpeechForm userSpeechForm) {
		this.appLogger.start("入室IDの更新日付の更新");
		
		try {
			LocalDateTime now         = LocalDateTime.now();
			EnterIdStatus enterId     = new EnterIdStatus(userSpeechForm.getEnter_id());
			EnterModel    enterModel  = this.enterService.select(enterId);
			RoomIdStatus  roomId      = new RoomIdStatus(enterModel.getRoom_id());
			
			this.enterService.updateUpdated_byId(now, enterId);
			this.roomService.updateUpdated_byId(now, roomId);
		} catch(DatabaseUpdateException ex) {
			// 更新不可
			// [ERROR]
			this.appLogger.info("throw: " + ex);
			return ;
		}
		
		this.appLogger.successed("入室IDの更新日付の更新成功");
	}
	
	/**
	 * リダイレクト設定
	 * @param userSpeechForm      投稿フォーム
	 * @param redirectAttributes  リダイレクト
	 */
	private void setRedirect(UserSpeechForm userSpeechForm, RedirectAttributes redirectAttributes) {
		String encryptNumber = CommonEncript.encrypt(userSpeechForm.getEnter_id());
		redirectAttributes.addAttribute(WebConsts.BIND_ENCRYPT_ENTER_ID, encryptNumber);
	}
}
