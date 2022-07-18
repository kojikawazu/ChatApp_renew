package com.example.demo.common.service;

import java.util.List;

import com.example.demo.app.entity.UserModel;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.UserNameEmail;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ユーザーサービス(インターフェース)
 *
 */
public interface UserService {

	void save(UserModel model);
	
	UserIdStatus save_returnId(UserModel model);
	
	void update(UserModel model);
	
	void update_passwd(UserNameEmailPassword user);
	
	void delete(UserIdStatus id);
	
	List<UserModel> getAll();
	
	UserModel select(UserIdStatus id);
	
	UserModel selectModel_subLoginId(LoginIdStatus loginId);
	
	UserIdStatus selectId_byNameEmailPasswd(UserNameEmailPassword user);
	
	boolean isSelect_byId(UserIdStatus id);
	
	boolean isSelect_byNameEmail(UserNameEmail user);
	
	boolean isSelect_byNameOrEmail(UserNameEmail user);
	
	boolean isSelect_byNameEmailForgotPW(UserNameEmailPassword user);
	
	boolean isSelect_byNameEmailPasswd(UserNameEmailPassword user);
}
