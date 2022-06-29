package com.example.demo.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.dao.EnterDao;
import com.example.demo.app.entity.EnterModel;
import com.example.demo.app.exception.WebMvcConfig;

/**
 * 入室サービス
 *
 */
@Service
public class EnterServiceUse implements EnterService {

	/**
	 * 入室Dao
	 */
	private EnterDao dao;
	
	/**
	 * コンストラクタ
	 * @param dao
	 */
	@Autowired
	public EnterServiceUse(EnterDao dao) {
		// TODO コンストラクタ
		this.dao = dao;
	}
	
	/**
	 * 入室情報保存
	 * @param model: 入室モデル
	 */
	@Override
	public void save(EnterModel model) {
		// TODO 保存
		this.dao.insert(model);
	}

	/**
	 * 入室情報保存
	 * @param model: 入室モデル
	 * return 入室ID
	 */
	@Override
	public int save_returnId(EnterModel model) {
		// TODO 保存(返却ID)
		return this.dao.insert_byId(model);
	}

	/**
	 * 更新
	 * @param model: 入室モデル
	 */
	@Override
	public void update(EnterModel model) {
		// TODO 更新
		if(this.dao.update(model) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * 更新
	 * @param room_id: ルームID
	 * @param manager_id: 管理ID
	 * @param sum: 入室最大数
	 * @param user_id: ユーザID
	 */
	@Override
	public void update_byUserId(int room_id, int manager_id, int sum, int user_id) {
		// TODO 更新
		if(this.dao.update_byUserId(room_id, manager_id, sum, user_id) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * 更新
	 * @param managerId: 管理ID
	 * @param id: 入室ID
	 */
	@Override
	public void updateManagerId_byId(int managerId, int id) {
		// TODO 更新
		if(this.dao.updateManagerId_byId(managerId, id) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 削除
	 * @param id: 入室ID
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
	 * return 入室モデルリスト
	 */
	@Override
	public List<EnterModel> getAll() {
		// TODO 全選択
		return this.dao.getAll();
	}

	/**
	 * 選択
	 * @param id: 入室ID
	 * return 入室モデル
	 */
	@Override
	public EnterModel select(int id) {
		// TODO IDによる選択
		return this.dao.select(id);
	}
	
	/**
	 * 選択
	 * @param userId: ユーザID
	 * return 入室ID
	 */
	@Override
	public int selectId_byUserId(int userId) {
		// TODO ユーザIDによるID取得
		return this.dao.selectId_byUserId(userId);
	}

	/**
	 * 入室データ有無確認
	 * @param id: 入室ID
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}
	
	/**
	 * 入室データ有無確認
	 * @param userId: ユーザID
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byUserId(int userId) {
		// TODO 入室しているユーザの有無の確認
		return this.dao.isSelect_byUserId(userId);
	}

	/**
	 * 入室数の取得
	 * @param roomId: ルームID
	 * return 入室数
	 */
	@Override
	public int getCount_roomId(int roomId) {
		// TODO ルームIDによる数の取得
		return this.dao.getCount_roomId(roomId);
	}

}
