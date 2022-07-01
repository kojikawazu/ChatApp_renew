package com.example.demo.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.app.dao.CommentDao;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.app.exception.WebMvcConfig;

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
	 * @param model: コメントモデル
	 */
	@Override
	public void save(CommentModel model) {
		// TODO 追加
		this.dao.insert(model);
	}

	/**
	 * コメントモデルの保存
	 * @param model: コメントモデル
	 * return コメントID
	 */
	@Override
	public int save_returnId(CommentModel model) {
		// TODO 追加(返却ID)
		return this.dao.insert_byId(model);
	}

	/**
	 * コメントモデル更新
	 * @param model: コメントモデル
	 */
	@Override
	public void update(CommentModel model) {
		// TODO 更新
		if( this.dao.update(model) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * コメント削除
	 * @param id: コメントID
	 */
	@Override
	public void delete(int id) {
		// TODO 削除
		if( this.dao.delete(id) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}
	
	/**
	 * コメント削除
	 * @param roomId: ルームID
	 */
	@Override
	public void delete_byRoomId(int roomId) {
		// TODO ルームIDによる削除
		if( this.dao.delete_byRoomId(roomId) == 0) {
			throw WebMvcConfig.NOT_FOUND();
		}
	}

	/**
	 * 全て選択
	 * return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> getAll() {
		// TODO 全選択
		return this.dao.getAll();
	}

	/**
	 * 選択
	 * @param id: コメントID
	 * return コメントモデル
	 */
	@Override
	public CommentModel select(int id) {
		// TODO IDによる選択
		return this.dao.select(id);
	}
	
	/**
	 * 選択
	 * @param roomId: ルームID
	 * return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> select_byRoomId(int roomId) {
		// TODO 自動生成されたメソッド・スタブ
		return this.dao.select_byRoomId(roomId);
	}

	/**
	 * コメント有無チェック
	 * @param id: コメントID
	 * return true あり false なし 
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無の確認
		return this.dao.isSelect_byId(id);
	}

}