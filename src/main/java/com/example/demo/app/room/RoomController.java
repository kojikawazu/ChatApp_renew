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
import com.example.demo.common.encrypt.CommonEncript;
import com.example.demo.common.log.ChatAppLogger;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.service.UserService;
import com.example.demo.common.session.SessionLoginId;
import com.example.demo.common.status.LoginIdStatus;

/**
 *  ---------------------------------------------------------------------------
 * 【ルームコントローラ】
 * @author nanai
 *  ---------------------------------------------------------------------------
 */
@Controller
@RequestMapping("/room")
public class RoomController implements SuperRoomController {

	/**
	 * サービス
	 */
	private UserService    userService;
	private RoomService    roomService;
	private LoginService   loginService;
	
	/**
	 * ログクラス
	 */
	private ChatAppLogger appLogger = ChatAppLogger.getInstance();
	
	/**
	 * セッションクラス
	 */
	@Autowired
	private SessionLoginId sessionLoginId;
	
	/** ルームタイトル */
	public static String ROOM_TITTLE  = "ルーム選択";
	
	/** ルームメッセージ */
	public static String ROOM_MESSAGE = "チャットルーム一覧です。";
	
	/**
	 * コンストラクタ
	 * @param userService
	 * @param roomService
	 */
	@Autowired
	public RoomController(
			UserService    userService,
			RoomService    roomService,
			LoginService   loginService) {
		this.userService    = userService;
		this.roomService    = roomService;
		this.loginService   = loginService;
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
			@RequestParam(value = WebConsts.BIND_ENCRYPT_LOGIN_ID, 
							required = false, 
							defaultValue = "wfssM4JI4nk=") String e_loginId,
			Model model,
			@ModelAttribute("noticeSuccess") String noticeSuccess,
			@ModelAttribute("noticeError")   String noticeError) {
		// ホーム画面
		this.appLogger.start("ルーム画面受信... loginId: " + e_loginId);
		
		// debugPrint
		System.out.println(
				( this.sessionLoginId.getLoginId().isBlank() ?
					"Not Session Login ID" :
						this.sessionLoginId.getLoginId()));
		
		// 復号化
		int login_id = Integer.parseInt(CommonEncript.decrypt(e_loginId));
		this.appLogger.info("復号化... loginId: " + login_id);
		
		if( login_id > 0 ) {
			// ログインチェック
			LoginIdStatus loginIdStatus = new LoginIdStatus(login_id);
			if( this.loginService.isSelect_byId(loginIdStatus) ) {
				this.appLogger.info("ログイン中 : loginId: " + login_id);
				// ログイン情報あり
				// ログイン情報設定
				UserModel userModel = this.userService.selectModel_subLoginId(new LoginIdStatus(login_id));
				model.addAttribute(WebConsts.BIND_USER_MODEL, userModel);
			} else {
				// ログイン情報なし
				login_id = 0;
				model.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
				return WebConsts.URL_REDIRECT_ROOM_INDEX;
			}
		} else if( login_id < 0 ) {
			// 負の数は0に設定してリダイレクト
			login_id = 0;
			model.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
			return WebConsts.URL_REDIRECT_ROOM_INDEX;
		}
		
		// ログインID設定
		model.addAttribute(WebConsts.BIND_LOGIN_ID, login_id);
		
		// ルームリスト拡張版取得
		List<RoomModelEx> roomModelListExList = this.changeRoomModel();
		model.addAttribute(WebConsts.BIND_ROOMMODEL_LIST, roomModelListExList);
		this.appLogger.info("ルーム一覧 : roomModelExList.size(): " + roomModelListExList.size());
		
		// ルームホーム画面設定
		this.setIndex(model);
		
		this.appLogger.start("ルーム画面完了: loginId: " + login_id);
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
