package com.example.demo.common.dao;

import java.util.List;

import com.example.demo.app.entity.UserModel;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.UserNameEmail;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ユーザDaoパターン(インターフェース)
 * @author nanai
 *
 */
public interface UserDao {
	
	void insert(UserModel model);
		
	UserIdStatus insert_returnId(UserModel model);
	
	int update(UserModel model);
	
	int update_passwd(UserNameEmailPassword user);
	
	int delete(UserIdStatus id);
	
	List<UserModel> getAll();
	
	UserModel select(UserIdStatus id);
	
	UserModel select_byId_subLoginId(LoginIdStatus loginId);
	
	UserIdStatus selectId_byNameEmailPass(UserNameEmailPassword user);
	
	boolean isSelect_byId(UserIdStatus id);
	
	boolean isSelect_byNameEmail(UserNameEmail user);
	
	boolean isSelect_byNameOrEmail(UserNameEmail user);
	
	boolean isSelect_byNameEmailForgotPW(UserNameEmailPassword user);
	
	boolean isSelect_byNameEmailPass(UserNameEmailPassword user);
}
