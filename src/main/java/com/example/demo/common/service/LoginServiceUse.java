package com.example.demo.common.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.LoginModelEx;
import com.example.demo.app.exception.WebMvcConfig;
import com.example.demo.common.dao.LoginDao;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ログインサービス
 * @author nanai
 *
 */
@Service
public class LoginServiceUse implements LoginService {

	/**
	 * ログインDao
	 */
	private LoginDao dao;
	
	/**
	 * コンストラクタ
	 * @param dao
	 */
	@Autowired
	public LoginServiceUse(LoginDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 保存
	 * @param model ログインモデル
	 */
	@Override
	public void save(LoginModel model) {
		// 保存
		this.dao.insert(model);
	}

	/**
	 * 保存
	 * @param model ログインモデル
	 * @return ログインID
	 */
	@Override
	public LoginIdStatus save_returnId(LoginModel model) {
		// 保存(返却ID)
		return this.dao.insert_returnId(model);
	}

	/**
	 * 更新
	 * @param model ログインモデル
	 */
	@Override
	public void update(LoginModel model) {
		// 更新
		if( this.dao.update(model) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * 更新
	 * @param roomId ルームID
	 * @param id     ログインID
	 * @return 更新日時
	 */
	@Override
	public LocalDateTime updateRoomId_byId(RoomIdStatus roomId, LoginIdStatus id) {
		// ルームID更新
		LocalDateTime now = LocalDateTime.now();
		if( this.dao.updateRoomId_byId(roomId, id, now) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return now;
	}

	/**
	 * 更新
	 * @param roomId ルームID
	 * @param userId ユーザID
	 * @return 更新日時
	 */
	@Override
	public LocalDateTime updateRoomId_byUserId(RoomIdStatus roomId, UserIdStatus userId) {
		// ユーザIDによるルームIDの更新
		LocalDateTime now = LocalDateTime.now();
		if( this.dao.updateRoomId_byUserId(roomId, userId, now) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return now;
	}
	
	/**
	 * 更新
	 * @param roomId   ルームID
	 * @param changeId 新しいルームID
	 * @return 更新日時
	 */
	@Override
	public LocalDateTime updateRoomId_byRoomId(RoomIdStatus roomId, RoomIdStatus changeId) {
		// ルームIDによるルームIDの更新
		LocalDateTime now = LocalDateTime.now();
		if( this.dao.updateRoomId_byRoomId(roomId, changeId, now) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return now;
	}
	
	/**
	 * 更新日付だけの更新
	 * @param updated 更新日付
	 * @param id      ログインID
	 */
	@Override
	public void updateUpdated_byId(LocalDateTime updated, LoginIdStatus id) {
		if( this.dao.updateUpdated_byId(updated, id) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 削除
	 * @param id ログインID
	 */
	@Override
	public void delete(LoginIdStatus id) {
		// 削除
		if( this.dao.delete(id) == 0 ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 全て選択
	 * @return ログインモデルリスト
	 */
	@Override
	public List<LoginModel> getAll() {
		// 全選択
		return this.dao.getAll();
	}

	/**
	 * 選択
	 * @param id ログインID
	 * @return ログインモデル
	 */
	@Override
	public LoginModel select(LoginIdStatus id) {
		// IDによる選択
		return this.dao.select(id);
	}

	/**
	 * 選択
	 * @param roomId ルームID
	 * @return ログインモデルリスト
	 */
	@Override
	public List<LoginModel> selectList_byRoomId(RoomIdStatus roomId) {
		// ルームIDによる選択
		return this.dao.selectList_byRoomId(roomId);
	}
	
	/**
	 * ルームIDによる選択+ユーザー名選択
	 * @param roomId ルームID
	 * @return ログインモデル拡張版リスト
	 */
	@Override
	public List<LoginModelEx> selectList_plusUserName_byRoomId(RoomIdStatus roomId) {
		// ルームIDによる選択+ユーザー名選択
		return this.dao.selectList_plusUserName_byRoomId(roomId);
	}
	
	/**
	 * 選択
	 * @param userId ユーザID
	 * @return ログインID
	 */
	@Override
	public LoginIdStatus selectId_byUserId(UserIdStatus userId) {
		// ユーザIDによるID取得
		return this.dao.selectId_byUserId(userId);
	}
	
	/**
	 * 選択
	 * @param userId ユーザID
	 * @return ログインモデル
	 */
	@Override
	public LoginModel select_byUserId(UserIdStatus userId) {
		// ユーザIDによる取得
		return this.dao.select_byuserId(userId);
	}
	
	/**
	 * 選択
	 * @param userId ユーザID
	 * @return ログインID
	 */
	@Override
	public RoomIdStatus selectRoomId_byUserId(UserIdStatus userId) {
		// ユーザIDによるルームIDの取得
		return this.dao.selectRoomId_byUserId(userId);
	}

	/**
	 * ログインモデルの有無確認
	 * @param id ログインID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(LoginIdStatus id) {
		// IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}

	/**
	 * ログインモデルの有無確認
	 * @param userId ユーザID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byUserId(UserIdStatus userId) {
		// ユーザIDによるIDの有無確認
		return this.dao.isSelect_byUserId(userId);
	}
}
