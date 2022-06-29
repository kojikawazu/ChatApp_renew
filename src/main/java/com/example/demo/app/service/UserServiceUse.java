package com.example.demo.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.dao.UserDao;
import com.example.demo.app.entity.UserModel;
import com.example.demo.app.exception.WebMvcConfig;

/**
 * ユーザサービス
 *
 */
@Service
public class UserServiceUse implements UserService {

	/**
	 * ユーザDao
	 */
	private UserDao dao;
	
	/**
	 * コンストラクタ
	 * @param dao
	 */
	@Autowired
	public UserServiceUse(UserDao dao) {
		// TODO コンストラクタ
		this.dao = dao;
	}
	
	/**
	 * 保存
	 * @param model: ユーザモデル
	 */
	@Override
	public void save(UserModel model) {
		// TODO 保存
		this.dao.insert(model);
	}

	/**
	 * 保存
	 * @param model: ユーザモデル
	 * return ユーザID
	 */
	@Override
	public int save_returnId(UserModel model) {
		// TODO 追加(返却ID)
		return this.dao.insert_byId(model);
	}

	/**
	 * 更新
	 * @param model: ユーザモデル
	 */
	@Override
	public void update(UserModel model) {
		// TODO 更新
		if(this.dao.update(model) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * パスワード変更
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * @param passwd: パスワード
	 */
	@Override
	public void update_passwd(String name, String email, String passwd) {
		// TODO パスワード更新
		if(this.dao.update_passwd(name, email, passwd) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 削除
	 * @param id: ユーザID
	 */
	@Override
	public void delete(int id) {
		// TODO 削除
		if(this.dao.delete(id) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 全て選択
	 * return ユーザモデルリスト
	 */
	@Override
	public List<UserModel> getAll() {
		// TODO 全選択
		return this.dao.getAll();
	}

	/**
	 * 選択
	 * @param id: ユーザID
	 * return ユーザモデル
	 */
	@Override
	public UserModel select(int id) {
		// TODO IDによる選択
		return this.dao.select(id);
	}
	
	/**
	 * ユーザID取得
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * @param passwd: パスワード
	 * return ユーザID
	 */
	@Override
	public int selectId_byNameEmailPasswd(String name, String email, String passwd) {
		// TODO IDの取得
		return this.dao.selectId_byNameEmailPass(name, email, passwd);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param id: ユーザID
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmail(String name, String email) {
		// TODO 有無確認
		return this.dao.isSelect_byNameEmail(name, email);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * @param forgot: 忘れたとき用パスワード
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmailForgotPW(String name, String email, String forgot) {
		// TODO 有無確認
		return this.dao.isSelect_byNameEmailForgotPW(name, email, forgot);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * @param passwd: パスワード
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmailPasswd(String name, String email, String passwd) {
		// TODO 有無確認
		return this.dao.isSelect_byNameEmailPass(name, email, passwd);
	}



}
