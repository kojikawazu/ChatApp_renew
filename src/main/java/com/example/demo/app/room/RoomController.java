package com.example.demo.app.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.RoomModelEx;
import com.example.demo.app.entity.UserModel;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.service.UserService;
import com.example.demo.common.status.LoginIdStatus;

/**
 *  ---------------------------------------------------------------------------
 * 【ルームコントローラ】
 * @author nanai
 *  ---------------------------------------------------------------------------
 */
@Controller
@RequestMapping("/room")
public class RoomController {

	/** ルームタイトル */
	public static String ROOM_TITTLE  = "ルーム選択";
	
	/** ルームメッセージ */
	public static String ROOM_MESSAGE = "チャットルーム一覧です。";
	
	/**
	 * サービス
	 */
	private UserService    userService;
	private RoomService    roomService;
	
	/**
	 * コンストラクタ
	 * @param userService
	 * @param roomService
	 */
	@Autowired
	public RoomController(UserService userService,
			RoomService roomService) {
		this.userService    = userService;
		this.roomService    = roomService;
	}
	
	/**
	 * ホーム受信
	 * @param login_id      ログインID
	 * @param model         モデル
	 * @param noticeSuccess 成功通知 
	 * @param noticeError   失敗通知
	 * @return Webパス(room/index)
	 */
	@GetMapping
	public String index(
			@RequestParam(value = "login_id", required = false, defaultValue = "0") int login_id,
			Model model,
			@ModelAttribute("noticeSuccess") String noticeSuccess,
			@ModelAttribute("noticeError")   String noticeError) {
		// ホーム画面
		
		// ログインID設定
		model.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
		if( login_id > 0) {
			// ログイン情報設定
			UserModel userModel = this.userService.selectModel_subLoginId(new LoginIdStatus(login_id));
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
		List<RoomModelEx> roomModelListExList = this.roomService.getAll_plusUserName_EnterCnt();
		return roomModelListExList;
	}
	
	/**
	 * ルームホーム画面設定
	 * @param model
	 */
	private void setIndex(Model model) {
		// ホーム画面設定
		model.addAttribute(WebConsts.BIND_TITLE, ROOM_TITTLE);
		model.addAttribute(WebConsts.BIND_CONT,  ROOM_MESSAGE);
	}
}
