package com.example.demo.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.UserModel;
import com.example.demo.app.exception.WebMvcConfig;
import com.example.demo.common.dao.UserDao;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.UserNameEmail;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ユーザサービス
 * @author nanai
 *
 */
@Service
public class UserServiceUse implements UserService {

	/**
	 * ユーザDaoクラス
	 */
	private UserDao dao;
	
	/**
	 * コンストラクタ
	 * @param dao ユーザDaoクラス
	 */
	@Autowired
	public UserServiceUse(UserDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 保存
	 * @param model ユーザモデル
	 */
	@Override
	public void save(UserModel model) {
		// 保存
		this.dao.insert(model);
	}

	/**
	 * 保存
	 * @param model ユーザモデル
	 * @return ユーザID
	 */
	@Override
	public UserIdStatus save_returnId(UserModel model) {
		// 追加(返却ID)
		return this.dao.insert_returnId(model);
	}

	/**
	 * 更新
	 * @param model ユーザモデル
	 */
	@Override
	public void update(UserModel model) {
		// 更新
		if( this.dao.update(model) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * パスワード変更
	 * @param user ユーザ名, Eメール, パスワードクラス
	 */
	@Override
	public void update_passwd(UserNameEmailPassword user) {
		// パスワード更新
		if( this.dao.update_passwd(user) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 削除
	 * @param id ユーザID
	 */
	@Override
	public void delete(UserIdStatus id) {
		// 削除
		if( this.dao.delete(id) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 全て選択
	 * @return ユーザモデルリスト
	 */
	@Override
	public List<UserModel> getAll() {
		// 全選択
		return this.dao.getAll();
	}

	/**
	 * 選択
	 * @param id ユーザID
	 * @return ユーザモデル
	 */
	@Override
	public UserModel select(UserIdStatus id) {
		// IDによる選択
		return this.dao.select(id);
	}
	
	/**
	 * ログインIDを元にユーザーモデルを選択する
	 * @param ログインID
	 * @return ユーザーモデル
	 */
	@Override
	public UserModel selectModel_subLoginId(LoginIdStatus loginId) {
		// ログインIDを元にユーザーモデルを選択する
		return this.dao.select_byId_subLoginId(loginId);
	}
	
	/**
	 * ユーザID取得
	 * @param user ユーザ名, Eメール, パスワードクラス
	 * @return ユーザID
	 */
	@Override
	public UserIdStatus selectId_byNameEmailPasswd(UserNameEmailPassword user) {
		// IDの取得
		return this.dao.selectId_byNameEmailPass(user);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param id ユーザID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(UserIdStatus id) {
		// IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param user ユーザ名, Eメールクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmail(UserNameEmail user) {
		// 有無確認
		return this.dao.isSelect_byNameEmail(user);
	}
	
	/**
	 * ユーザー名、又はEメールの有無確認
	 * @param user ユーザ名, Eメールクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameOrEmail(UserNameEmail user) {
		// 有無確認
		return this.dao.isSelect_byNameOrEmail(user);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param user ユーザ名, Eメール, パスワードクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmailForgotPW(UserNameEmailPassword user) {
		// 有無確認
		return this.dao.isSelect_byNameEmailForgotPW(user);
	}

	/**
	 * ユーザモデルの有無確認
	 * @param user ユーザ名, Eメール, パスワードクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmailPasswd(UserNameEmailPassword user) {
		// 有無確認
		return this.dao.isSelect_byNameEmailPass(user);
	}
}
