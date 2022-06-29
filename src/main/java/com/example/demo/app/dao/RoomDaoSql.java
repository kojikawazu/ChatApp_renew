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

import com.example.demo.app.entity.RoomModel;

/**
 * ルームDaoパターン
 *
 */
@Repository
public class RoomDaoSql implements RoomDao {

	/**
	 * Field
	 */
	private JdbcTemplate jdbcTemp;
	
	/**
	 * コンストラクタ
	 * @param jdbcTemp
	 */
	@Autowired
	public RoomDaoSql(JdbcTemplate jdbcTemp) {
		// TODO コンストラクタ
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model: ルームモデル
	 */
	@Override
	public void insert(RoomModel model) {
		// TODO 追加
		this.jdbcTemp.update("INSERT INTO chat_room(name, comment, tag, max_roomsum, user_id, created, updated) VALUES(?,?,?,?,?,?,?)",
				model.getName(),
				model.getComment(),
				model.getTag(),
				model.getMax_roomsum(),
				model.getUser_id(),
				model.getCreated(),
				model.getUpdated());
	}

	/**
	 * 追加処理
	 * @param model: ルームモデル
	 * return ルームID
	 */
	@Override
	public int insert_byId(RoomModel model) {
		// TODO 追加(return id)
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO chat_room(name, comment, tag, max_roomsum, user_id, created, updated) VALUES(?,?,?,?,?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		Timestamp timestamp2 = Timestamp.valueOf(model.getUpdated());
		
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
		return keyHolder.getKey().intValue();
	}

	/**
	 * 更新処理
	 * @param model: ルームモデル
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int update(RoomModel model) {
		// TODO 更新
		return jdbcTemp.update("UPDATE chat_room SET name = ?, comment = ?, tag = ?, max_roomsum = ?, user_id = ?, created = ?, updated = ? WHERE id = ?",
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
	 * @param userId: ユーザID
	 * @param newId: 新規ユーザID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int updateUserId_byUserId(int userId, int newId) {
		// TODO 更新
		return jdbcTemp.update("UPDATE chat_room SET user_id = ? WHERE user_id = ?",
				newId, userId);
	}

	/**
	 * 削除処理
	 * @param id: ルームID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int delete(int id) {
		// TODO 削除
		return jdbcTemp.update("DELETE FROM chat_room WHERE id = ?", id);
	}

	/**
	 * 全て選択
	 * return ルームモデルリスト
	 */
	@Override
	public List<RoomModel> getAll() {
		// TODO 全選択
		String sql = "SELECT id, name, comment, tag, max_roomsum, user_id, created, updated FROM chat_room ORDER BY updated DESC";
		List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
		List<RoomModel> list = new ArrayList<RoomModel>();
		
		for( Map<String, Object> result : resultList ) {
			RoomModel model = new RoomModel();
			model.setId((int)result.get("id"));
			model.setName((String)result.get("name"));
			model.setComment((String)result.get("comment"));
			model.setTag((String)result.get("tag"));
			model.setMax_roomsum((int)result.get("max_roomsum"));
			model.setUser_id((int)result.get("user_id"));
			model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			model.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
			list.add(model);
		}
		return list;
	}

	/**
	 * ルームIDによる選択
	 * @param id: ルームID
	 * return ルームモデル
	 */
	@Override
	public RoomModel select(int id) {
		// TODO IDによるデータ取得
		String sql = "SELECT id, name, comment, tag, max_roomsum, user_id, created, updated FROM chat_room WHERE id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, id);
			
		RoomModel model = new RoomModel();
		model.setId((int)result.get("id"));
		model.setName((String)result.get("name"));
		model.setComment((String)result.get("comment"));
		model.setTag((String)result.get("tag"));
		model.setMax_roomsum((int)result.get("max_roomsum"));
		model.setUser_id((int)result.get("user_id"));
		model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		model.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
		
		return model;
	}

	/**
	 * ルームIDによる有無チェック
	 * @param id: ルームID
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無チェック
		String sql = "SELECT id FROM chat_room WHERE id = ?";
		return jdbcTemp.query(sql, new Object[]{ id }, rs -> {
			return rs.next() ? true : false;	
		});
	}

}
