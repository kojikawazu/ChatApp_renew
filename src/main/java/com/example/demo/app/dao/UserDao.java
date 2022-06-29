package com.example.demo.app.dao;

import java.util.List;

import com.example.demo.app.entity.UserModel;

/**
 * ユーザDaoパターン
 * @author nanai
 *
 */
public interface UserDao {
	
	void insert(UserModel model);
		
	int insert_byId(UserModel model);
	
	int update(UserModel model);
	
	int update_passwd(String name, String email, String passwd);
	
	int delete(int id);
	
	List<UserModel> getAll();
	
	UserModel select(int id);
	
	int selectId_byNameEmailPass(String name, String email, String passwd);
	
	boolean isSelect_byId(int id);
	
	boolean isSelect_byNameEmail(String name, String email);
	
	boolean isSelect_byNameEmailForgotPW(String name, String email, String forgot);
	
	boolean isSelect_byNameEmailPass(String name, String email, String passwd);

}
