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
import com.example.demo.app.service.CommentService;
import com.example.demo.app.service.EnterService;
import com.example.demo.app.service.LoginService;
import com.example.demo.app.service.RoomService;
import com.example.demo.app.service.UserService;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 【チャットコントローラー】
 */
@Controller
@RequestMapping("/chat")
public class ChatController {
	
	/**
	 * サービス 
	 */
	private UserService userService;
	private RoomService roomService;
	private CommentService commentService;
	private LoginService loginService;
	private EnterService enterService;
	
	/**
	 * 【コンストラクタ】
	 * @param userService
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public ChatController(
			UserService userService,
			RoomService roomService,
			CommentService commentService,
			LoginService loginService,
			EnterService enterService) {
		this.userService = userService;
		this.roomService = roomService;
		this.commentService = commentService;
		this.loginService = loginService;
		this.enterService = enterService;
	}
	
	/**
	 * 【チャットホーム】
	 * @param enter_id : 入室ID
	 * @param model : モデル
	 * @param roomLeaveForm : 
	 * @param redirectAttributes
	 * @return Webパス (redirect:/room or chat/room)
	 */
	@GetMapping
	public String index(
			@RequestParam(value = "enter_id", required = false, defaultValue = "0") int enter_id,
			Model model,
			RoomLeaveForm roomLeaveForm,
			RedirectAttributes redirectAttributes) {
		
		// エラーチェック
		if( !this.enterService.isSelect_byId(enter_id) ) {
			// 入室IDがない場合、ルーム画面へリダイレクト
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		EnterModel enterModel = this.enterService.select(enter_id);
		if(!this.isEnterCheck(enterModel, redirectAttributes)) {
			// 入室NGな場合、ルーム画面へリダイレクト
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ビュー画面へ設定
		this.setIndex(enterModel, model, enter_id);
		
		// ルーム画面へ
		return WebConsts.URL_CHAT_INDEX;
	}
	
	/**
	 * 【コメント情報受信】
	 * @param userSpeechForm: ユーザコメントフォーム
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス(redirect:/chat)
	 */
	@PostMapping("/speech")
	public String speech(
			UserSpeechForm userSpeechForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		
		// エラーチェック
		if(userSpeechForm.getComment() == null || userSpeechForm.getComment().isBlank()) {
			// コメント追加なしでリダイレクト
			redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, userSpeechForm.getEnter_id());
			return WebConsts.URL_REDIRECT_CHAT_INDEX;
		}
		
		// コメント情報追加
		this.setSpeech(userSpeechForm);
		
		// コメント追加後にリダイレクト
		redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, userSpeechForm.getEnter_id());
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	
	/**
	 * 【ログアウト受信】
	 * @param roomOutForm: ログアウトフォーム
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping("/outroom")
	public String outroom(
			RoomOutForm roomOutForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// 退室
		
		// 情報取得
		int enter_id = roomOutForm.getEnter_id();
		EnterModel enterModel = this.enterService.select(enter_id);
		int user_id = enterModel.getUser_id();
		int login_id = this.loginService.selectId_byUserId(new UserIdStatus(user_id));
		
		// 退室情報の通知
		this.setComment(user_id, enterModel.getRoom_id());
		
		// ログイン情報のルームIDの初期化
		this.loginService.updateRoomId_byUserId(
				new RoomIdStatus(0), 
				new UserIdStatus(user_id));
		
		// 入室情報の削除
		this.enterService.delete(enter_id);
		
		// リダイレクト設定
		redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * 【部屋閉鎖受信】
	 * @param roomOutForm: ログアウトフォーム
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス(redirect:/room)
	 */
	@PostMapping("/closeroom")
	public String closeroom(
			RoomOutForm roomOutForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// 部屋閉鎖
		
		// 情報取得
		int enter_id = roomOutForm.getEnter_id();
		EnterModel enterModel = this.enterService.select(enter_id);
		int user_id = enterModel.getUser_id();
		int login_id = this.loginService.selectId_byUserId(new UserIdStatus(user_id));
		int room_id = enterModel.getRoom_id();
		
		// ログイン情報のルームIDの初期化
		this.loginService.updateRoomId_byRoomId(
				new RoomIdStatus(room_id), 
				new RoomIdStatus(0));
		
		// コメント情報の削除(ルームID)
		this.commentService.delete_byRoomId(room_id);
		
		// 自身の入室情報の削除
		this.enterService.delete(enter_id);
		
		// ルーム情報の削除
		this.roomService.delete(enterModel.getRoom_id());
		
		// リダイレクト設定
		redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
		return WebConsts.URL_REDIRECT_ROOM_INDEX;
	}
	
	/**
	 * 【強制退室】
	 * @param roomLeaveForm: 強制退室フォーム
	 * @param model: モデル
	 * @param redirectAttributes: リダイレクト
	 * @return Webパス: (redirect:/chat)
	 */
	@PostMapping("/userclose")
	public String user_closeroom(
			RoomLeaveForm roomLeaveForm,
			Model model,
			RedirectAttributes redirectAttributes) {
		// TODO 強制退室
		
		// エラーチェック
		if(roomLeaveForm.getIn_id() == 0) {
			// 何もせずリダイレクト
			redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, roomLeaveForm.getEnter_id());
			return WebConsts.URL_REDIRECT_CHAT_INDEX;
		}
		
		// 情報取得
		EnterModel enterModel = this.enterService.select(roomLeaveForm.getEnter_id());
		LoginModel loginModel = this.loginService.select(new LoginIdStatus(roomLeaveForm.getIn_id()));
		UserModel userModel = this.userService.select(new UserIdStatus(loginModel.getUser_id()));
		
		// 強制退室通知
		CommentModel commentModel = new CommentModel();
		commentModel.setComment(userModel.getName() +  "さんを強制退室させました。");
		commentModel.setRoom_id(enterModel.getRoom_id());
		commentModel.setUser_id(enterModel.getUser_id());
		commentModel.setCreated(LocalDateTime.now());
		this.commentService.save(commentModel);
		
		// ログイン情報のルーム番号の初期化
		this.loginService.updateRoomId_byId(
				new RoomIdStatus(0), 
				new LoginIdStatus(roomLeaveForm.getIn_id()));
		
		// チャットリダイレクト
		redirectAttributes.addAttribute(WebConsts.BIND_ENTER_ID, roomLeaveForm.getEnter_id());
		return WebConsts.URL_REDIRECT_CHAT_INDEX;
	}
	
	// -------------------------------------------------------------------------------------------------------
	
	/**
	 * 【ルームモデル拡張版へ変換】
	 * @param roomModel: ルームモデル
	 * @return ルームモデル拡張版
	 */
	public RoomModelEx changeRoomModel(RoomModel roomModel) {
		// TODO ルームモデル拡張版へ変換
		RoomModelEx roomModelEx = new RoomModelEx(roomModel);
		int count = this.enterService.getCount_roomId(roomModel.getId());
		roomModelEx.setEnterCnt(count);
		return roomModelEx;
	}
	
	/**
	 * コメントモデルリスト拡張版へ変換
	 * @param commentModelList: コメントモデルリスト
	 * @return コメントモデルリスト拡張版
	 */
	public List<CommentModelEx> changeCommentModelList(List<CommentModel> commentModelList){
		// TODO コメントモデルリスト拡張版へ変換
		List<CommentModelEx> list = new ArrayList<CommentModelEx>();
		
		// 拡張版へ移行
		for( CommentModel commentModel : commentModelList) {
			CommentModelEx ex = new CommentModelEx(commentModel);
			
			UserModel userModel =  this.userService.select(new UserIdStatus(commentModel.getUser_id()));
			ex.setUserName(userModel.getName());
			list.add(ex);
		}
		
		// 旧版は使用しないので、旧リストの中身をクリア
		commentModelList.clear();
		
		return list;
	}
	
	/**
	 * ログインモデルリスト拡張版へ変換
	 * @param loginModelList: ログインモデルリスト
	 * @return ログインモデルリスト拡張版
	 */
	private List<LoginModelEx> changeLoginModelList(List<LoginModel> loginModelList) {
		// ログインリスト拡張版に変換
		List<LoginModelEx> loginModelExList = new ArrayList<LoginModelEx>();
		
		// 拡張版へ移行
		for( LoginModel lnModel : loginModelList) {
			UserModel usModel = this.userService.select(new UserIdStatus(lnModel.getUser_id()));
			LoginModelEx ex = new LoginModelEx(lnModel, usModel.getName());
			loginModelExList.add(ex);
		}
		
		// 旧版は使用しないので、旧リストの中身をクリア
		loginModelList.clear();
		
		return loginModelExList;
	}
	
	/**
	 * [入室チェック]
	 * @param enterModel : 入室モデル
	 * @param redirectAttributes : リダイレクト
	 * @return true : 入室OK false : 入室NG
	 */
	private boolean isEnterCheck(
			EnterModel enterModel,
			RedirectAttributes redirectAttributes) {
		
		// エラーチェック
		if( !this.roomService.isSelect_byId(enterModel.getRoom_id()) ) {
			// 既に閉鎖されている
			
			// ユーザIDからログインIDを取得
			int login_id = this.loginService.selectId_byUserId(new UserIdStatus(enterModel.getUser_id()));
			
			// ログインIDをリダイレクト
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "部屋が閉鎖されました。");
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
			return false;
		}
		
		int room_id = this.loginService.selectRoomId_byUserId(new UserIdStatus(enterModel.getUser_id()));
		if( room_id == 0) {
			// 強制退室された
			
			// ログインIDを削除
			this.enterService.delete(enterModel.getId());
			
			// ユーザIDからログインIDを取得
			int login_id = this.loginService.selectId_byUserId(new UserIdStatus(enterModel.getUser_id()));
			
			// ログインIDをリダイレクト
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "強制退室されました。");
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
			return false;
		}
		
		// 入室OK
		return true;
	}
	
	/**
	 * 【チャットルーム設定】
	 * @param enterModel: 入室モデル
	 * @param model: モデル
	 * @param enter_id: 入室ID
	 */
	private void setIndex(EnterModel enterModel, Model model, int enter_id) {
		// TODO チャットルーム設定
		
		// ホストユーザチェック
		LoginModel lnModel = this.loginService.select_byUserId(new UserIdStatus(enterModel.getManager_id()));
		if(lnModel.getRoom_id() != enterModel.getRoom_id()) {
			// 既にホストが変更されている場合
			
			// ホストユーザIDを変更
			this.enterService.updateManagerId_byId(enterModel.getUser_id(), enterModel.getId());
			this.roomService.updateUserId_byUserId(enterModel.getManager_id(), enterModel.getUser_id());
			enterModel.setManager_id(enterModel.getUser_id());
		}
		
		// ルーム情報を取得
		RoomModel roomModel = this.roomService.select(enterModel.getRoom_id());
		UserModel userModel = this.userService.select(new UserIdStatus(enterModel.getUser_id()));
		UserModel hostModel = this.userService.select(new UserIdStatus(enterModel.getManager_id()));
		int room_id = this.loginService.selectRoomId_byUserId(new UserIdStatus(enterModel.getUser_id()));
		List<CommentModel> commentModelList = this.commentService.select_byRoomId(enterModel.getRoom_id());
		List<LoginModel> loginModelList = this.loginService.selectList_byRoomId(new RoomIdStatus(room_id));
		
		// 拡張版変換
		RoomModelEx roomModelEx = this.changeRoomModel(roomModel);
		List<CommentModelEx> commentModelExList = this.changeCommentModelList(commentModelList);
		List<LoginModelEx> loginModelExList = this.changeLoginModelList(loginModelList);
		
		// データをバインド
		model.addAttribute(WebConsts.BIND_TITLE, "チャットルームへようこそ");
		model.addAttribute(WebConsts.BIND_CONT, "自由に遊んでください。");
		model.addAttribute(WebConsts.BIND_ENTER_ID, enter_id);
		model.addAttribute(WebConsts.BIND_ROOMMODEL, roomModelEx);
		model.addAttribute(WebConsts.BIND_USERMODEL, userModel);
		model.addAttribute(WebConsts.BIND_HOSTMODEL, hostModel);
		model.addAttribute(WebConsts.BIND_COMMENTMODEL_LIST, commentModelExList);
		model.addAttribute(WebConsts.BIND_LOGINMODEL_LIST, loginModelExList);
	}
	
	/**
	 * 【退室コメント通知】
	 * @param user_id: ユーザID
	 * @param room_id: ルームID
	 */
	private void setComment(int user_id, int room_id) {
		// TODO 退室コメント通知
		CommentModel commentModel = new CommentModel();
		commentModel.setUser_id(user_id);
		commentModel.setRoom_id(room_id);
		commentModel.setComment("退室されました。");
		commentModel.setCreated(LocalDateTime.now());
		this.commentService.save(commentModel);
	}
	
	/**
	 * 【コメント追加処理】
	 * @param userSpeechForm
	 */
	public void setSpeech(UserSpeechForm userSpeechForm) {
		// TODO 投稿情報の追加
		CommentModel commentModel = new CommentModel();
		commentModel.setComment(userSpeechForm.getComment());
		commentModel.setRoom_id(userSpeechForm.getRoom_id());
		commentModel.setUser_id(userSpeechForm.getUser_id());
		commentModel.setCreated(LocalDateTime.now());
		this.commentService.save(commentModel);
	}
	
}
