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
		EnterIdStatus enterIdStatus = new EnterIdStatus(enter_id);
		
		// エラーチェック
		if( !this.enterService.isSelect_byId(enterIdStatus) ) {
			// 入室IDがない場合、ルーム画面へリダイレクト
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		EnterModel enterModel = this.enterService.select(enterIdStatus);
		if(!this.isEnterCheck(enterModel, redirectAttributes)) {
			// 入室NGな場合、ルーム画面へリダイレクト
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ビュー画面へ設定
		this.setIndex(enterModel, model, enter_id);
		
		// ルーム画面へ
		return WebConsts.URL_CHAT_INDEX;
	}
	
	// -------------------------------------------------------------------------------------------------------
	
	/**
	 * 【ルームモデル拡張版へ変換】
	 * @param roomModel ルームモデル
	 * @return ルームモデル拡張版
	 */
	public RoomModelEx changeRoomModel(RoomModel roomModel) {
		// ルームモデル拡張版へ変換
		int count = this.enterService.getCount_roomId(new RoomIdStatus(roomModel.getId()));
		RoomModelEx roomModelEx = new RoomModelEx(
				roomModel,
				new NameWord(""),
				new RoomEnterCntNumber(count));
		return roomModelEx;
	}
	
	/**
	 * コメントモデルリスト拡張版へ変換
	 * @param commentModelList コメントモデルリスト
	 * @return コメントモデルリスト拡張版
	 */
	public List<CommentModelEx> changeCommentModelList(List<CommentModel> commentModelList){
		// コメントモデルリスト拡張版へ変換
		List<CommentModelEx> list = new ArrayList<CommentModelEx>();
		
		// 拡張版へ移行
		for( CommentModel commentModel : commentModelList ) {
			UserModel userModel =  this.userService.select(new UserIdStatus(commentModel.getUser_id()));
			CommentModelEx ex = new CommentModelEx(
					commentModel,
					new NameWord(userModel.getName()));
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
			LoginModelEx ex = new LoginModelEx(
					lnModel, 
					new NameWord(usModel.getName()));
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
		if( !this.roomService.isSelect_byId(new RoomIdStatus(enterModel.getRoom_id())) ) {
			// 既に閉鎖されている
			
			// ユーザIDからログインIDを取得
			LoginIdStatus login_id = this.loginService.selectId_byUserId(new UserIdStatus(enterModel.getUser_id()));
			
			// ログインIDをリダイレクト
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "部屋が閉鎖されました。");
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id.getId());
			return false;
		}
		
		RoomIdStatus room_id = this.loginService.selectRoomId_byUserId(new UserIdStatus(enterModel.getUser_id()));
		if( room_id.getId() == WebConsts.ERROR_DB_STATUS ) {
			// 強制退室された
			
			// ログインIDを削除
			this.enterService.delete(new EnterIdStatus(enterModel.getId()));
			
			// ユーザIDからログインIDを取得
			LoginIdStatus login_id = this.loginService.selectId_byUserId(new UserIdStatus(enterModel.getUser_id()));
			
			// ログインIDをリダイレクト
			redirectAttributes.addFlashAttribute(WebConsts.BIND_NOTICE_ERROR, "強制退室されました。");
			redirectAttributes.addAttribute(WebConsts.BIND_LOGIN_ID, login_id.getId());
			return false;
		}
		
		// 入室OK
		return true;
	}
	
	/**
	 * 【チャットルーム設定】
	 * @param enterModel 入室モデル
	 * @param model      モデル
	 * @param enter_id   入室ID
	 */
	private void setIndex(EnterModel enterModel, Model model, int enter_id) {
		// チャットルーム設定
		
		// ホストユーザチェック
		LoginModel lnModel = this.loginService.select_byUserId(new UserIdStatus(enterModel.getManager_id()));
		if(lnModel.getRoom_id() != enterModel.getRoom_id()) {
			// 既にホストが変更されている場合
			
			// ホストユーザIDを変更
			this.enterService.updateManagerId_byId(
					new UserIdStatus(enterModel.getUser_id()), 
					new EnterIdStatus(enterModel.getId()));
			this.roomService.updateUserId_byUserId(
					new UserIdStatus(enterModel.getManager_id()),
					new UserIdStatus(enterModel.getUser_id()));
			//enterModel.setManager_id(enterModel.getUser_id());
			
			enterModel = new EnterModel(
					new EnterIdStatus(enterModel.getId()),
					new RoomIdStatus(enterModel.getRoom_id()),
					new UserIdStatus(enterModel.getUser_id()),
					new UserIdStatus(enterModel.getUser_id()),
					new RoomMaxNumber(enterModel.getMax_sum()),
					enterModel.getCreated());
		}
		
		// ルーム情報を取得
		RoomModel          roomModel        = this.roomService.select(new RoomIdStatus(enterModel.getRoom_id()));
		UserModel          userModel        = this.userService.select(new UserIdStatus(enterModel.getUser_id()));
		UserModel          hostModel        = this.userService.select(new UserIdStatus(enterModel.getManager_id()));
		RoomIdStatus       room_id          = this.loginService.selectRoomId_byUserId(new UserIdStatus(enterModel.getUser_id()));
		List<CommentModel> commentModelList = this.commentService.select_byRoomId(new RoomIdStatus(enterModel.getRoom_id()));
		List<LoginModel>   loginModelList   = this.loginService.selectList_byRoomId(room_id);
		
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
}
