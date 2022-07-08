package com.example.demo.app.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.UserModel;
import com.example.demo.common.service.CommentService;
import com.example.demo.common.service.EnterService;
import com.example.demo.common.service.LoginService;
import com.example.demo.common.service.RoomService;
import com.example.demo.common.service.UserService;

/**
 * 管理コントロール
 *
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {

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
	public ManagerController(
			UserService userService,
			RoomService roomService,
			CommentService commentService,
			LoginService loginService,
			EnterService enterService) {
		// TODO コンストラクタ
		this.userService = userService;
		this.roomService = roomService;
		this.commentService = commentService;
		this.loginService = loginService;
		this.enterService = enterService;
	}
	
	/**
	 * 管理ホーム受信
	 * @param model
	 * @return
	 */
	@GetMapping
	public String index(Model model) {
		// TODO ホーム画面
		
		List<UserModel> userModelList = this.userService.getAll();
		List<RoomModel> roomModelList = this.roomService.getAll();
		List<LoginModel> loginModelList = this.loginService.getAll();
		List<EnterModel> enterModelList = this.enterService.getAll();
		List<CommentModel> commentModelList = this.commentService.getAll();
		
		model.addAttribute(WebConsts.BIND_USERMODEL_LIST, userModelList);
		model.addAttribute(WebConsts.BIND_ROOMMODEL_LIST, roomModelList);
		model.addAttribute(WebConsts.BIND_LOGINMODEL_LIST, loginModelList);
		model.addAttribute(WebConsts.BIND_ENTERMODEL_LIST, enterModelList);
		model.addAttribute(WebConsts.BIND_COMMENTMODEL_LIST, commentModelList);
		
		model.addAttribute(WebConsts.BIND_TITLE, "管理人画面");
		model.addAttribute(WebConsts.BIND_CONT, "DB表示の為の画面です。");
		
		return WebConsts.URL_MANAGER_INDEX;
	}
}
