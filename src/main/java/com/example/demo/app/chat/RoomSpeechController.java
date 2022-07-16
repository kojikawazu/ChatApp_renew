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
import com.example.demo.app.form.UserSpeechForm;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.CommentService;
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
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/**
	 * コンストラクタ
	 * @param commentService
	 */
	@Autowired
	public RoomSpeechController(
			CommentService commentService) {
		this.commentService = commentService;
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
		if(userSpeechForm.getComment() == null || userSpeechForm.getComment().isBlank()) {
			// コメント追加なしでリダイレクト
			this.appLogger.info("コメントなし");
			
			redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, userSpeechForm.getEnter_id());
			return WebConsts.URL_REDIRECT_CHAT_INDEX;
		}
		
		// コメント情報追加
		this.setSpeech(userSpeechForm);
		
		// コメント追加後にリダイレクト
		redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, userSpeechForm.getEnter_id());
		
		this.appLogger.successed("コメント成功");
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * 【コメント追加処理】
	 * @param userSpeechForm
	 */
	public void setSpeech(UserSpeechForm userSpeechForm) {
		this.appLogger.start("投稿情報の追加");
		
		CommentModel commentModel = new CommentModel(
				new ChatCommentWord(userSpeechForm.getComment()),
				new RoomIdStatus(userSpeechForm.getRoom_id()),
				new UserIdStatus(userSpeechForm.getUser_id()),
				LocalDateTime.now());
		this.commentService.save(commentModel);
		
		this.appLogger.successed("投稿情報の追加成功");
	}
}
