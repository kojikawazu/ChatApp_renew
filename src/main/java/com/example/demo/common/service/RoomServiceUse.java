package com.example.demo.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.exception.WebMvcConfig;
import com.example.demo.common.dao.RoomDao;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ルームサービス
 * @author nanai
 *
 */
@Service
public class RoomServiceUse implements RoomService {

	/**
	 * ルームDao
	 */
	private RoomDao dao;
	
	/**
	 * コンストラクタ
	 * @param dao
	 */
	@Autowired
	public RoomServiceUse(RoomDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 保存
	 * @param model ルームモデル
	 */
	@Override
	public void save(RoomModel model) {
		// 保存
		this.dao.insert(model);
	}

	/**
	 * 保存
	 * @param model ルームモデル
	 * @return      ルームID
	 */
	@Override
	public RoomIdStatus save_returnId(RoomModel model) {
		// 追加(返却ID)
		return this.dao.insert_byId(model);
	}

	/**
	 * 更新
	 * @param model ルームモデル
	 */
	@Override
	public void update(RoomModel model) {
		// 更新
		if( this.dao.update(model) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * 更新
	 * @param userId ユーザID
	 * @param newId  新しいユーザID
	 */
	@Override
	public void updateUserId_byUserId(UserIdStatus userId, UserIdStatus newId) {
		// 更新
		if( this.dao.updateUserId_byUserId(userId, newId) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 削除
	 * @param id ルームID
	 */
	@Override
	public void delete(RoomIdStatus id) {
		// 削除
		if( this.dao.delete(id) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 全て選択
	 * @return ルームモデルリスト
	 */
	@Override
	public List<RoomModel> getAll() {
		// 全選択
		return this.dao.getAll();
	}

	/**
	 * 選択
	 * @param id ルームID
	 * @return ルームモデル
	 */
	@Override
	public RoomModel select(RoomIdStatus id) {
		// IDによる選択
		return this.dao.select(id);
	}

	/**
	 * ルームモデルの有無確認
	 * @param id ルームID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(RoomIdStatus id) {
		// IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}
}
