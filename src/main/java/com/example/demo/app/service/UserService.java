package com.example.demo.app.service;

import java.util.List;

import com.example.demo.app.entity.UserModel;

/**
 * ユーザーサービス
 *
 */
public interface UserService {

	void save(UserModel model);
	
	int save_returnId(UserModel model);
	
	void update(UserModel model);
	
	void update_passwd(String name, String email, String passwd);
	
	void delete(int id);
	
	List<UserModel> getAll();
	
	UserModel select(int id);
	
	int selectId_byNameEmailPasswd(String name, String email, String passwd);
	
	boolean isSelect_byId(int id);
	
	boolean isSelect_byNameEmail(String name, String email);
	
	boolean isSelect_byNameEmailForgotPW(String name, String email, String forgot);
	
	boolean isSelect_byNameEmailPasswd(String name, String email, String passwd);
}
