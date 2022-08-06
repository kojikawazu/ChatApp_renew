package com.example.demo.common.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.RoomModelEx;
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
		return this.dao.insert_returnId(model);
	}

	/**
	 * 更新
	 * @param model ルームモデル
	 */
	@Override
	public void update(RoomModel model) {
		// 更新
		if( this.dao.update(model) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_DATA_UPDATE();
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
			throw WebMvcConfig.NOT_DATA_UPDATE();
		}
	}
	
	/**
	 * 更新日付の更新
	 * @param updated 更新日付
	 * @param id      入室ID
	 * @throws        更新されない場合
	 */
	@Override
	public void updateUpdated_byId(LocalDateTime updated, RoomIdStatus id) {
		// 更新
		if( this.dao.updateUpdated_byId(updated, id) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_DATA_UPDATE();
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
		List<RoomModel> list = this.dao.getAll();
		if(list.isEmpty()) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return list;
	}
	
	/**
	 * 全選択+ユーザー名+入室数を選択
	 * @return ルームモデル拡張版
	 */
	@Override
	public List<RoomModelEx> getAll_plusUserName_EnterCnt() {
		// 全選択+ユーザー名+入室数を選択
		List<RoomModelEx> list = this.dao.getAll_plusUserName_EnterCnt();
		if(list.isEmpty()) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return list;
	}

	/**
	 * 選択
	 * @param id ルームID
	 * @return ルームモデル
	 */
	@Override
	public RoomModel select(RoomIdStatus id) {
		// IDによる選択
		RoomModel model = this.dao.select(id);
		if(model == null) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return model;
	}
	
	/**
	 * IDによる選択+ユーザー名+入室数を選択
	 * @param id ルームID
	 * @return ルームモデル
	 */
	@Override
	public RoomModelEx select_plusUserName_EnterCnt(RoomIdStatus id) {
		// IDによる選択+ユーザー名+入室数を選択
		RoomModelEx model = this.dao.select_plusUserName(id);
		if(model == null) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return model;
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
