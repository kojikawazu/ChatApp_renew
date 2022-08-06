package com.example.demo.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.entity.CommentModelEx;
import com.example.demo.app.exception.WebMvcConfig;
import com.example.demo.common.dao.CommentDao;
import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.RoomIdStatus;

/**
 * コメントサービス
 * @author nanai
 *
 */
@Service
public class CommentServiceUse implements CommentService {

	/**
	 * コメントDao
	 */
	private CommentDao dao;
	
	/**
	 * コンストラクタ
	 * @param dao
	 */
	@Autowired
	public CommentServiceUse(CommentDao dao) {
		this.dao = dao;
	}
	
	/**
	 * コメントモデルの保存
	 * @param model コメントモデル
	 */
	@Override
	public void save(CommentModel model) {
		// 追加
		this.dao.insert(model);
	}

	/**
	 * コメントモデルの保存
	 * @param model コメントモデル
	 * @return      コメントID
	 */
	@Override
	public CommentIdStatus save_returnId(CommentModel model) {
		// 追加(返却ID)
		return this.dao.insert_byId(model);
	}

	/**
	 * コメントモデル更新
	 * @param model コメントモデル
	 */
	@Override
	public void update(CommentModel model) {
		// 更新
		if( this.dao.update(model) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_DATA_UPDATE();
		}
	}

	/**
	 * コメント削除
	 * @param id コメントID
	 */
	@Override
	public void delete(CommentIdStatus id) {
		// 削除
		if( this.dao.delete(id) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * コメント削除
	 * @param roomId ルームID
	 */
	@Override
	public void delete_byRoomId(RoomIdStatus roomId) {
		// ルームIDによる削除
		if( this.dao.delete_byRoomId(roomId) <= WebConsts.ERROR_DB_STATUS ) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 全て選択
	 * @return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> getAll() {
		// 全選択
		List<CommentModel> list = this.dao.getAll();
		if(list.isEmpty()) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return list;
	}
	
	/**
	 * 全選択 + ユーザー名を選択
	 * @return コメント漏れる拡張版リスト
	 */
	@Override
	public List<CommentModelEx> getAll_plusUserName() {
		// 全選択+ユーザー名を選択
		List<CommentModelEx> list = this.dao.getAll_plusUserName();
		if(list.isEmpty()) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return list;
	}

	/**
	 * 選択
	 * @param id コメントID
	 * @return コメントモデル
	 */
	@Override
	public CommentModel select(CommentIdStatus id) {
		// IDによる選択
		CommentModel model = this.dao.select(id);
		if(model == null) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return model;
	}
	
	/**
	 * 選択
	 * @param roomId ルームID
	 * @return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> select_byRoomId(RoomIdStatus roomId) {
		// 選択
		List<CommentModel> list = this.dao.select_byRoomId(roomId);
		if(list.isEmpty()) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return list;
	}
	
	/**
	 * ルームIDにより選択+ユーザー名選択
	 * @param ルームID
	 * @return コメントモデル拡張版リスト
	 */
	@Override
	public List<CommentModelEx> select_plusUserName_byRoomId(RoomIdStatus roomId) {
		// 選択+ユーザー名選択
		List<CommentModelEx> list = this.dao.select_plusUserName_byRoomId(roomId);
		if(list.isEmpty()) {
			throw WebMvcConfig.NOT_FOUND();
		}
		return list;
	}

	/**
	 * コメント有無チェック
	 * @param id コメントID
	 * @return true あり false なし 
	 */
	@Override
	public boolean isSelect_byId(CommentIdStatus id) {
		// IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}
}
