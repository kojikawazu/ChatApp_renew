package com.example.demo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.app.entity.CommentModel;

/**
 * 【コメントDaoパターン】
 */
@Repository
public class CommentDaoSql implements CommentDao {

	/**
	 * Field
	 */
	private JdbcTemplate jdbcTemp;
	
	/**
	 * コンストラクタ
	 * @param jdbcTemp
	 */
	@Autowired
	public CommentDaoSql(JdbcTemplate jdbcTemp) {
		// TODO コンストラクタ
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model: コメントモデル
	 */
	@Override
	public void insert(CommentModel model) {
		// TODO 追加
		this.jdbcTemp.update("INSERT INTO chat_comment(comment, room_id, user_id, created) VALUES(?,?,?,?)",
				model.getComment(),
				model.getRoom_id(),
				model.getUser_id(),
				model.getCreated());
	}
	
	/**
	 * 追加処理
	 * @param model: コメントモデル
	 * return コメントID
	 */
	@Override
	public int insert_byId(CommentModel model) {
		// TODO 追加(return id)
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO chat_comment(comment, room_id, user_id, created) VALUES(?,?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		
       jdbcTemp.update(new PreparedStatementCreator() {
           public PreparedStatement createPreparedStatement(
               Connection connection) throws SQLException {
                   PreparedStatement ps = connection.prepareStatement(
                       sql, new String[] { "id" });
                   ps.setString(1, model.getComment());
                   ps.setInt(2, model.getRoom_id());
                   ps.setInt(3, model.getUser_id());
                   ps.setTimestamp(4, timestamp);
                   return ps;
               }
           }, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 更新処理
	 * @param model: コメントモデル
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int update(CommentModel model) {
		// TODO 更新
		return jdbcTemp.update("UPDATE chat_comment SET comment = ?, room_id = ?, user_id = ?, created = ? WHERE id = ?",
				model.getComment(),
				model.getRoom_id(),
				model.getUser_id(),
				model.getCreated(),
				model.getId());
	}

	/**
	 * 削除処理
	 * @param id: コメントID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int delete(int id) {
		// TODO 削除
		return this.jdbcTemp.update("DELETE FROM chat_comment WHERE id = ?", id);
	}
	
	/**
	 * 削除処理
	 * @param id: ルームID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int delete_byRoomId(int roomId) {
		// TODO 削除
		return this.jdbcTemp.update("DELETE FROM chat_comment WHERE room_id = ?", roomId);
	}

	/**
	 * 全選択
	 * return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> getAll() {
		// TODO 全選択
		String sql = "SELECT id, comment, room_id, user_id, created FROM chat_comment ORDER BY created DESC";
		List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
		List<CommentModel> list = new ArrayList<CommentModel>();
		
		for( Map<String, Object> result : resultList ) {
			CommentModel model = new CommentModel();
			model.setId((int)result.get("id"));
			model.setRoom_id((int)result.get("room_id"));
			model.setUser_id((int)result.get("user_id"));
			model.setComment((String)result.get("comment"));
			model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(model);
		}
		return list;
	}

	/**
	 * コメントIDから選択
	 * @param id: コメントID
	 * return コメントモデル
	 */
	@Override
	public CommentModel select(int id) {
		// TODO IDによるデータ取得
		String sql = "SELECT id, comment, room_id, user_id, created FROM chat_comment WHERE id = ? ORDER BY created DESC";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, id);
			
		CommentModel model = new CommentModel();
		model.setId((int)result.get("id"));
		model.setRoom_id((int)result.get("room_id"));
		model.setUser_id((int)result.get("user_id"));
		model.setComment((String)result.get("comment"));
		model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		
		return model;
	}
	
	/**
	 * ルームIDから選択
	 * @param roomId: ルームID
	 * return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> select_byRoomId(int roomId) {
		// TODO ルームIDによる選択
		String sql = "SELECT id, comment, room_id, user_id, created FROM chat_comment WHERE room_id = ? ORDER BY created DESC";
		List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql, roomId);
		List<CommentModel> list = new ArrayList<CommentModel>();
		
		for( Map<String, Object> result : resultList ) {
			CommentModel model = new CommentModel();
			model.setId((int)result.get("id"));
			model.setRoom_id((int)result.get("room_id"));
			model.setUser_id((int)result.get("user_id"));
			model.setComment((String)result.get("comment"));
			model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(model);
		}
		return list;
	}

	/**
	 * コメントの有無チェック
	 * @param id: ルームID
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無チェック
		String sql = "SELECT id FROM chat_comment WHERE id = ?";
		return jdbcTemp.query(sql, new Object[]{ id }, rs -> {
			return rs.next() ? true : false;	
		});
	}

}
