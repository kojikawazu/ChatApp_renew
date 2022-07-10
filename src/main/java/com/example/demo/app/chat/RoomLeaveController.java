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
import com.example.demo.app.form.RoomLeaveForm;
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
	private CommentService commentService;
	private LoginService   loginService;
	private EnterService   enterService;
	
	/**
	 * コンストラクタ
	 * @param userService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public RoomLeaveController(
			UserService    userService,
			CommentService commentService,
			LoginService   loginService,
			EnterService   enterService) {
		this.userService    = userService;
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
	public String user_closeroom(
			RoomLeaveForm roomLeaveForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// 強制退室
		
		// エラーチェック
		if(roomLeaveForm.getIn_id() == 0) {
			// 何もせずリダイレクト
			redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, roomLeaveForm.getEnter_id());
			return WebConsts.URL_REDIRECT_CHAT_INDEX;
		}
		
		// 強制退室通知
		this.noticeLeave(roomLeaveForm);
		
		// ログイン情報のルーム番号の初期化
		this.initRoomId_byLoginId(roomLeaveForm);
		
		// チャットリダイレクト
		redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, roomLeaveForm.getEnter_id());
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	/**
	 * 強制退室通知
	 * @param roomLeaveForm
	 */
	private void noticeLeave(RoomLeaveForm roomLeaveForm) {
		// 情報取得
		EnterModel enterModel = this.enterService.select(new EnterIdStatus(roomLeaveForm.getEnter_id()));
		LoginModel loginModel = this.loginService.select(new LoginIdStatus(roomLeaveForm.getIn_id()));
		UserModel  userModel  = this.userService.select(new UserIdStatus(loginModel.getUser_id()));
		
		// 強制退室通知
		CommentModel commentModel = new CommentModel(
				new ChatCommentWord(userModel.getName() +  "さんを強制退室させました。"),
				new RoomIdStatus(enterModel.getRoom_id()),
				new UserIdStatus(enterModel.getUser_id()),
				LocalDateTime.now());
		this.commentService.save(commentModel);
	}
	
	/**
	 * ログインIDによるルームIDの初期化
	 * @param roomLeaveForm
	 */
	private void initRoomId_byLoginId(RoomLeaveForm roomLeaveForm) {
		this.loginService.updateRoomId_byId(
				new RoomIdStatus(0), 
				new LoginIdStatus(roomLeaveForm.getIn_id()));
	}
}
