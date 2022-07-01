package com.example.demo.app.room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.RoomModelEx;
import com.example.demo.app.entity.UserModel;
import com.example.demo.app.service.CommentService;
import com.example.demo.app.service.EnterService;
import com.example.demo.app.service.LoginService;
import com.example.demo.app.service.RoomService;
import com.example.demo.app.service.UserService;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ルームコントローラ
 */
@Controller
@RequestMapping("/room")
public class RoomController {

	/**
	 * サービス
	 */
	private UserService userService;
	private RoomService roomService;
	private CommentService commentService;
	private LoginService loginService;
	private EnterService enterService;
	
	/**
	 * コンストラクタ
	 * @param userService
	 * @param roomService
	 * @param commentService
	 * @param loginService
	 * @param enterService
	 */
	@Autowired
	public RoomController(UserService userService,
			RoomService roomService,
			CommentService commentService,
			LoginService loginService,
			EnterService enterService) {
		// コンストラクタ
		this.userService = userService;
		this.roomService = roomService;
		this.commentService = commentService;
		this.loginService = loginService;
		this.enterService = enterService;
	}
	
	/**
	 * ホーム受信
	 * @param login_id: ログインID
	 * @param model: モデル
	 * @param noticeSuccess: 成功通知 
	 * @param noticeError: 失敗通知
	 * @return Webパス(room/index)
	 */
	@GetMapping
	public String index(
			@RequestParam(value = "login_id", required = false, defaultValue = "0") int login_id,
			Model model,
			@ModelAttribute("noticeSuccess") String noticeSuccess,
			@ModelAttribute("noticeError") String noticeError) {
		// ホーム画面
		
		// ログインID設定
		model.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
		if( login_id > 0) {
			// ログイン情報設定
			LoginModel loginModel = this.loginService.select(new LoginIdStatus(login_id));
			UserModel userModel = this.userService.select(new UserIdStatus(loginModel.getUser_id()));
			model.addAttribute(WebConsts.BIND_USER_MODEL, userModel);
		}
		
		// ルームリスト拡張版取得
		List<RoomModelEx> roomModelListExList = this.changeRoomModel();
		model.addAttribute(WebConsts.BIND_ROOMMODEL_LIST, roomModelListExList);
		
		// ルームホーム画面設定
		this.setIndex(model);
		
		return WebConsts.URL_ROOM_INDEX;
	}
	
	// ----------------------------------------------
	
	/**
	 * ルームモデル拡張版取得
	 * @return ルームモデル拡張版
	 */
	private List<RoomModelEx> changeRoomModel(){
		// ルームリストを拡張版へ変換
		List<RoomModel> roomModelList = this.roomService.getAll();
		List<RoomModelEx> roomModelListExList = new ArrayList<RoomModelEx>();
		for( RoomModel roomModel : roomModelList){
			RoomModelEx newModelEx = new RoomModelEx(roomModel);
			UserModel uModel =  this.userService.select(new UserIdStatus(roomModel.getUser_id()));
			// ユーザ名取得
			newModelEx.setUserName(uModel.getName());
			// 入室数取得
			int count = this.enterService.getCount_roomId(roomModel.getId());
			newModelEx.setEnterCnt(count);
			roomModelListExList.add(newModelEx);
		}
		roomModelList.clear();
		return roomModelListExList;
	}
	
	/**
	 * ルームホーム画面設定
	 * @param model
	 */
	private void setIndex(Model model) {
		// ホーム画面設定
		model.addAttribute(WebConsts.BIND_TITLE, "ルーム選択");
		model.addAttribute(WebConsts.BIND_CONT, "チャットルーム一覧です。");
	}
	
}
