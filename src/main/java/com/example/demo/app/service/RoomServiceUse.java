package com.example.demo.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.dao.RoomDao;
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.exception.WebMvcConfig;

/**
 * ルームサービス
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
		// TODO コンストラクタ
		this.dao = dao;
	}
	
	/**
	 * 保存
	 * @param model: ルームモデル
	 */
	@Override
	public void save(RoomModel model) {
		// TODO 保存
		this.dao.insert(model);
	}

	/**
	 * 保存
	 * @param model: ルームモデル
	 * return ルームID
	 */
	@Override
	public int save_returnId(RoomModel model) {
		// TODO 追加(返却ID)
		return this.dao.insert_byId(model);
	}

	/**
	 * 更新
	 * @param model: ルームモデル
	 */
	@Override
	public void update(RoomModel model) {
		// TODO 更新
		if(this.dao.update(model) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * 更新
	 * @param userId: ユーザID
	 * @param newId: 新しいユーザID
	 */
	@Override
	public void updateUserId_byUserId(int userId, int newId) {
		// TODO 更新
		if(this.dao.updateUserId_byUserId(userId, newId) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 削除
	 * @param id: ルームID
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
	 * reutrn ルームモデルリスト
	 */
	@Override
	public List<RoomModel> getAll() {
		// TODO 全選択
		return this.dao.getAll();
	}

	/**
	 * 選択
	 * @param id: ルームID
	 * return ルームモデル
	 */
	@Override
	public RoomModel select(int id) {
		// TODO IDによる選択
		return this.dao.select(id);
	}

	/**
	 * ルームモデルの有無確認
	 * @param id: ルームID
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}



}
