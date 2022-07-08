package com.example.demo.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.app.config.WebConsts;
import com.example.demo.app.entity.CommentModel;
import com.example.demo.common.status.CommentIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.ChatCommentWord;

/**
 * 【コメントDaoパターン】
 * @author nanai
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
		// コンストラクタ
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model コメントモデル
	 */
	@Override
	public void insert(CommentModel model) {
		// 追加
		if(model == null)	return ;
		
		try {
			this.jdbcTemp.update(
					"INSERT INTO chat_comment(comment, room_id, user_id, created) VALUES(?,?,?,?)",
					model.getComment(),
					model.getRoom_id(),
					model.getUser_id(),
					model.getCreated());	
		} catch(DataAccessException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 追加処理
	 * @param model コメントモデル
	 * @return コメントID
	 */
	@Override
	public int insert_byId(CommentModel model) {
		// 追加(return id)
		if(model == null)	return WebConsts.ERROR_NUMBER;
		int return_key = 0;
		
		try {
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
	       return_key = keyHolder.getKey().intValue();
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			return_key = WebConsts.ERROR_NUMBER;
		}
		
		
		return return_key;
	}

	/**
	 * 更新処理
	 * @param model コメントモデル
	 * @return 0 失敗 それ以外 成功
	 */
	@Override
	public int update(CommentModel model) {
		// 更新
		if(model == null)	return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_comment SET comment = ?, room_id = ?, user_id = ?, created = ? WHERE id = ?",
				model.getComment(),
				model.getRoom_id(),
				model.getUser_id(),
				model.getCreated(),
				model.getId());
	}

	/**
	 * 削除処理
	 * @param id コメントID
	 * @return 0 失敗 それ以外 成功
	 */
	@Override
	public int delete(CommentIdStatus id) {
		// 削除
		if(id == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"DELETE FROM chat_comment WHERE id = ?", 
				id.getId());
	}
	
	/**
	 * 削除処理
	 * @param id ルームID
	 * @return 0 失敗 それ以外 成功
	 */
	@Override
	public int delete_byRoomId(RoomIdStatus roomId) {
		// 削除
		if(roomId == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"DELETE FROM chat_comment WHERE room_id = ?",
				roomId.getId());
	}

	/**
	 * 全選択
	 * @return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> getAll() {
		// 全選択
		
		List<CommentModel> list = new ArrayList<CommentModel>();
		try {
			String sql = "SELECT id, comment, room_id, user_id, created FROM chat_comment ORDER BY created DESC";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
			
			for( Map<String, Object> result : resultList ) {
				CommentModel model = new CommentModel(
						new CommentIdStatus((int)result.get("id")),
						new ChatCommentWord((String)result.get("comment")),
						new RoomIdStatus((int)result.get("room_id")),
						new UserIdStatus((int)result.get("user_id")),
						((Timestamp)result.get("created")).toLocalDateTime());
				list.add(model);
			}
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			list.clear();
		}
		return list;
	}

	/**
	 * コメントIDから選択
	 * @param id コメントID
	 * @return コメントモデル
	 */
	@Override
	public CommentModel select(CommentIdStatus id) {
		// IDによるデータ取得
		if(id == null)	return new CommentModel(null);
		
		CommentModel model = new CommentModel(null);
		try {
			String sql = "SELECT id, comment, room_id, user_id, created FROM chat_comment WHERE id = ? ORDER BY created DESC";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, id.getId());
				
			model = new CommentModel(
					new CommentIdStatus((int)result.get("id")),
					new ChatCommentWord((String)result.get("comment")),
					new RoomIdStatus((int)result.get("room_id")),
					new UserIdStatus((int)result.get("user_id")),
					((Timestamp)result.get("created")).toLocalDateTime());		
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			model = new CommentModel(null);
		}
		return model;
	}
	
	/**
	 * ルームIDから選択
	 * @param roomId ルームID
	 * @return コメントモデルリスト
	 */
	@Override
	public List<CommentModel> select_byRoomId(RoomIdStatus roomId) {
		// ルームIDによる選択
		List<CommentModel> list = new ArrayList<CommentModel>();
		if(roomId == null)	return list;
		
		try {
			String sql = "SELECT id, comment, room_id, user_id, created FROM chat_comment WHERE room_id = ? ORDER BY created DESC";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql, roomId.getId());
			
			for( Map<String, Object> result : resultList ) {
				CommentModel model = new CommentModel(
						new CommentIdStatus((int)result.get("id")),
						new ChatCommentWord((String)result.get("comment")),
						new RoomIdStatus((int)result.get("room_id")),
						new UserIdStatus((int)result.get("user_id")),
						((Timestamp)result.get("created")).toLocalDateTime());
				list.add(model);
			}
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			list.clear();
		}
		return list;
	}

	/**
	 * コメントの有無チェック
	 * @param id ルームID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(CommentIdStatus id) {
		// IDによる有無チェック
		if(id == null)	return false;
		
		String sql = "SELECT id FROM chat_comment WHERE id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ id.getId() },
				new int[] { Types.INTEGER },
				rs -> {
			return rs.next() ? true : false;	
		});
	}
}
