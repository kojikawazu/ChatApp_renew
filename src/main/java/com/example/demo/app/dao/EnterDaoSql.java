package com.example.demo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.app.entity.EnterModel;

/**
 * 入室Daoパターン
 *
 */
@Repository
public class EnterDaoSql implements EnterDao {

	/**
	 * Field
	 */
	private JdbcTemplate jdbcTemp;
	
	/**
	 * コンストラクタ
	 * @param jdbcTemp
	 */
	@Autowired
	public EnterDaoSql(JdbcTemplate jdbcTemp) {
		// TODO コンストラクタ
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model: 入室モデル
	 */
	@Override
	public void insert(EnterModel model) {
		// TODO 追加
		this.jdbcTemp.update("INSERT INTO chat_enter(room_id, user_id, manager_id, max_sum, created) VALUES(?,?,?,?,?)",
				model.getRoom_id(),
				model.getUser_id(),
				model.getManager_id(),
				model.getMax_sum(),
				model.getCreated());
	}

	/**
	 * 追加処理
	 * @param model: 入室モデル
	 * return 入室ID
	 */
	@Override
	public int insert_byId(EnterModel model) {
		// TODO 追加(return id)
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO chat_enter(room_id, user_id, manager_id, max_sum, created) VALUES(?,?,?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		
       jdbcTemp.update(new PreparedStatementCreator() {
           public PreparedStatement createPreparedStatement(
               Connection connection) throws SQLException {
                   PreparedStatement ps = connection.prepareStatement(
                       sql, new String[] { "id" });
                   ps.setInt(1, model.getRoom_id());
                   ps.setInt(2, model.getUser_id());
                   ps.setInt(3, model.getManager_id());
                   ps.setInt(4, model.getMax_sum());
                   ps.setTimestamp(5, timestamp);
                   return ps;
               }
           }, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 更新処理
	 * @param model: 入室モデル
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int update(EnterModel model) {
		// TODO 更新
		return jdbcTemp.update("UPDATE chat_enter SET room_id = ?, user_id = ?, manager_id = ?, max_sum = ?, created = ? WHERE id = ?",
				model.getRoom_id(),
				model.getUser_id(),
				model.getManager_id(),
				model.getMax_sum(),
				model.getCreated(),
				model.getId());
	}
	
	/**
	 * ユーザIDから更新処理
	 * @param room_id: ルームID
	 * @param manager_id: 管理ID
	 * @param sum: ユーザ合計
	 * @param user_id: ユーザID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int update_byUserId(int room_id, int manager_id, int sum, int user_id) {
		// TODO ユーザIDによる更新
		return jdbcTemp.update("UPDATE chat_enter SET room_id = ?, manager_id = ?, max_sum = ?, created = ? WHERE user_id = ?",
				room_id,
				manager_id,
				sum,
				LocalDateTime.now(),
				user_id);
	}
	
	/**
	 * 管理IDから更新処理
	 * @param manager_id: 管理ID
	 * @param id: 入室ID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int updateManagerId_byId(int managerId, int id) {
		// TODO ホスト番号更新
		return jdbcTemp.update("UPDATE chat_enter SET manager_id = ?, created = ? WHERE id = ?",
				managerId,
				LocalDateTime.now(),
				id);
	}

	/**
	 * 削除処理
	 * @param id: 入室ID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int delete(int id) {
		// TODO 削除
		return this.jdbcTemp.update("DELETE FROM chat_enter WHERE id = ?", id);
	}

	/**
	 * 全選択
	 * return 入室モデルリスト
	 */
	@Override
	public List<EnterModel> getAll() {
		// TODO 全選択
		String sql = "SELECT id, room_id, user_id, manager_id, max_sum, created FROM chat_enter";
		List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
		List<EnterModel> list = new ArrayList<EnterModel>();
		
		for( Map<String, Object> result : resultList ) {
			EnterModel model = new EnterModel();
			model.setId((int)result.get("id"));
			model.setRoom_id((int)result.get("room_id"));
			model.setUser_id((int)result.get("user_id"));
			model.setManager_id((int)result.get("manager_id"));
			model.setMax_sum((int)result.get("max_sum"));
			model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(model);
		}
		return list;
	}

	/**
	 * 入室IDから選択
	 * @param id: 入室ID
	 * return 入室モデル
	 */
	@Override
	public EnterModel select(int id) {
		// TODO IDによるデータ取得
		String sql = "SELECT id, room_id, user_id, manager_id, max_sum, created FROM chat_enter WHERE id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, id);
			
		EnterModel model = new EnterModel();
		model.setId((int)result.get("id"));
		model.setRoom_id((int)result.get("room_id"));
		model.setUser_id((int)result.get("user_id"));
		model.setManager_id((int)result.get("manager_id"));
		model.setMax_sum((int)result.get("max_sum"));
		model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		return model;
	}
	
	/**
	 * ユーザIDから入室ID選択
	 * @param userId: ユーザID
	 * return 入室ID
	 */
	@Override
	public int selectId_byUserId(int userId) {
		// TODO ユーザIDによるID取得
		String sql = "SELECT id FROM chat_enter WHERE user_id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, userId);
		int id = (int)result.get("id");
		return id;
	}

	/**
	 * 入室IDによる有無チェック
	 * @param id: 入室ID
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無チェック
		String sql = "SELECT id FROM chat_enter WHERE id = ?";
		return jdbcTemp.query(sql, new Object[]{ id }, rs -> {
			return rs.next() ? true : false;	
		});
	}

	/**
	 * ルームIDによる入室数の取得
	 * @param roomId: ルームID
	 * return ログイン数
	 */
	@Override
	public int getCount_roomId(int roomId) {
		// TODO ログインしている数の取得
		String sql = "SELECT COUNT(*) AS COUNTER FROM chat_enter WHERE room_id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, roomId);
		int count = Integer.parseInt(result.get("counter").toString());
		return count;
	}

	/**
	 * ユーザIDによる有無チェック
	 * @param userId: ユーザID
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byUserId(int userId) {
		// TODO 入室しているユーザの有無の確認
		String sql = "SELECT id FROM chat_enter WHERE user_id = ?";
		return jdbcTemp.query(sql, new Object[]{ userId }, rs -> {
			return rs.next() ? true : false;	
		});
	}


}
