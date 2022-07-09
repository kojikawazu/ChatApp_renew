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
import com.example.demo.app.entity.RoomModel;
import com.example.demo.app.entity.UserModel;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.RoomCommentWord;
import com.example.demo.common.word.RoomNameWord;
import com.example.demo.common.word.RoomTagWord;

/**
 * ルームDaoパターン
 * @author nanai
 *
 */
@Repository
public class RoomDaoSql implements RoomDao {

	/**
	 * Field
	 * 
	 */
	private JdbcTemplate jdbcTemp;	/** jdbcテンプレート */
	
	/**
	 * コンストラクタ
	 * @param jdbcTemp
	 * 
	 */
	@Autowired
	public RoomDaoSql(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model ルームモデル
	 */
	@Override
	public void insert(RoomModel model) {
		// 追加
		if (model == null)	return;
		
		try {
			this.jdbcTemp.update(
					"INSERT INTO chat_room(name, comment, tag, max_roomsum, user_id, created, updated) VALUES(?,?,?,?,?,?,?)",
					model.getName(),
					model.getComment(),
					model.getTag(),
					model.getMax_roomsum(),
					model.getUser_id(),
					model.getCreated(),
					model.getUpdated());
		} catch(DataAccessException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 追加処理
	 * @param model ルームモデル
	 * @return      ルームID 失敗した場合は-1。
	 */
	@Override
	public RoomIdStatus insert_byId(RoomModel model) {
		// 追加(return id)
		if(model == null) return new RoomIdStatus(WebConsts.ERROR_NUMBER);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO chat_room(name, comment, tag, max_roomsum, user_id, created, updated) VALUES(?,?,?,?,?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		Timestamp timestamp2 = Timestamp.valueOf(model.getUpdated());
		int return_key = 0;
		
		try {
			 jdbcTemp.update(new PreparedStatementCreator() {
			       public PreparedStatement createPreparedStatement(
			           Connection connection) throws SQLException {
			               PreparedStatement ps = connection.prepareStatement(
			                   sql, new String[] { "id" });
			           ps.setString(1, model.getName());
			           ps.setString(2, model.getComment());
			           ps.setString(3, model.getTag());
			           ps.setInt(4, model.getMax_roomsum());
			           ps.setInt(5, model.getUser_id());
			           ps.setTimestamp(6, timestamp);
			           ps.setTimestamp(7, timestamp2);
			           return ps;
			       }
			   }, keyHolder);
			 return_key = keyHolder.getKey().intValue();
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			return_key = WebConsts.ERROR_NUMBER;
		}
      
		return new RoomIdStatus(return_key);
	}

	/**
	 * 更新処理
	 * @param  model ルームモデル
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int update(RoomModel model) {
		// 更新
		if(model == null) return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_room SET name = ?, comment = ?, tag = ?, max_roomsum = ?, user_id = ?, created = ?, updated = ? WHERE id = ?",
				model.getName(),
				model.getComment(),
				model.getTag(),
				model.getMax_roomsum(),
				model.getUser_id(),
				model.getCreated(),
				model.getUpdated(),
				model.getId());
	}
	
	/**
	 * ユーザIDから新規ユーザIDに更新処理
	 * @param userId ユーザID
	 * @param newId 新規ユーザID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int updateUserId_byUserId(UserIdStatus userId, UserIdStatus newId) {
		// 更新
		if(userId == null || newId == null) return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_room SET user_id = ? WHERE user_id = ?",
				newId.getId(), 
				userId.getId());
	}

	/**
	 * 削除処理
	 * @param  id ルームID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int delete(RoomIdStatus id) {
		// 削除
		if(id == null) return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"DELETE FROM chat_room WHERE id = ?", 
				id.getId());
	}

	/**
	 * 全て選択
	 * @return ルームモデルリスト
	 */
	@Override
	public List<RoomModel> getAll() {
		// 全選択
		List<RoomModel> list = new ArrayList<RoomModel>();
		try {
			String sql = "SELECT id, name, comment, tag, max_roomsum, user_id, created, updated FROM chat_room ORDER BY updated DESC";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
			
			for( Map<String, Object> result : resultList ) {
				RoomModel model = new RoomModel(
						new RoomIdStatus((int)result.get("id")),
						new RoomNameWord((String)result.get("name")),
						new RoomCommentWord((String)result.get("comment")),
						new RoomTagWord((String)result.get("tag")),
						new RoomMaxNumber((int)result.get("max_roomsum")),
						new UserIdStatus((int)result.get("user_id")),
						((Timestamp)result.get("created")).toLocalDateTime(),
						((Timestamp)result.get("updated")).toLocalDateTime());
				list.add(model);
			}
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			list.clear();
		}
		return list;
	}

	/**
	 * ルームIDによる選択
	 * @param  id ルームID
	 * @return ルームモデル
	 */
	@Override
	public RoomModel select(RoomIdStatus id) {
		// IDによるデータ取得
		if(id == null) return new RoomModel(null);
		
		RoomModel model = null;
		try {
			String sql = "SELECT id, name, comment, tag, max_roomsum, user_id, created, updated FROM chat_room WHERE id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, id.getId());
				
			model = new RoomModel(
					new RoomIdStatus((int)result.get("id")),
					new RoomNameWord((String)result.get("name")),
					new RoomCommentWord((String)result.get("comment")),
					new RoomTagWord((String)result.get("tag")),
					new RoomMaxNumber((int)result.get("max_roomsum")),
					new UserIdStatus((int)result.get("user_id")),
					((Timestamp)result.get("created")).toLocalDateTime(),
					((Timestamp)result.get("updated")).toLocalDateTime());
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			model = new RoomModel(null);
		}
		return model;
	}

	/**
	 * ルームIDによる有無チェック
	 * @param  id ルームID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(RoomIdStatus id) {
		// IDによる有無チェック
		if(id == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_room WHERE id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ id.getId() }, 
				new int[] {	Types.INTEGER },
				rs -> {
			return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
		});
	}
}
