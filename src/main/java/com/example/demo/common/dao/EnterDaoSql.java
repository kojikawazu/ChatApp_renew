package com.example.demo.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
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
import com.example.demo.app.entity.EnterModel;
import com.example.demo.common.number.RoomMaxNumber;
import com.example.demo.common.status.EnterIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * 入室Daoパターン
 * @author nanai
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
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model 入室モデル
	 */
	@Override
	public void insert(EnterModel model) {
		// 追加
		if(model == null)	return ;
		
		try {
			this.jdbcTemp.update(
					"INSERT INTO chat_enter(room_id, user_id, manager_id, max_sum, created) VALUES(?,?,?,?,?)",
					model.getRoom_id(),
					model.getUser_id(),
					model.getManager_id(),
					model.getMax_sum(),
					model.getCreated());
		} catch(DataAccessException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 追加処理
	 * @param model  入室モデル
	 * @return 入室ID 失敗した場合は-1。
	 */
	@Override
	public EnterIdStatus insert_byId(EnterModel model) {
		// 追加(return id)
		if(model == null)	return new EnterIdStatus(WebConsts.ERROR_NUMBER);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO chat_enter(room_id, user_id, manager_id, max_sum, created) VALUES(?,?,?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		int return_key = 0;
		
		try {
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
			 return_key = keyHolder.getKey().intValue();
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			return_key = WebConsts.ERROR_NUMBER;
		}
		return new EnterIdStatus(return_key);
	}

	/**
	 * 更新処理
	 * @param model 入室モデル
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int update(EnterModel model) {
		// 更新
		if(model == null)	return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_enter SET room_id = ?, user_id = ?, manager_id = ?, max_sum = ?, created = ? WHERE id = ?",
				model.getRoom_id(),
				model.getUser_id(),
				model.getManager_id(),
				model.getMax_sum(),
				model.getCreated(),
				model.getId());
	}
	
	/**
	 * ユーザIDから更新処理
	 * @param room_id    ルームID
	 * @param manager_id 管理ID
	 * @param sum        ユーザ合計
	 * @param user_id    ユーザID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int update_byUserId(RoomIdStatus room_id, UserIdStatus manager_id, RoomMaxNumber sum, UserIdStatus user_id) {
		// ユーザIDによる更新
		if(room_id == null  ||
			user_id == null ||
			sum == null     ||
			user_id == null) return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_enter SET room_id = ?, manager_id = ?, max_sum = ?, created = ? WHERE user_id = ?",
				room_id.getId(),
				manager_id.getId(),
				sum.getNumber(),
				LocalDateTime.now(),
				user_id.getId());
	}
	
	/**
	 * 管理IDから更新処理
	 * @param  manager_id 管理ID
	 * @param  id         入室ID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int updateManagerId_byId(UserIdStatus managerId, EnterIdStatus id) {
		// ホスト番号更新
		if(managerId == null || id == null)	return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_enter SET manager_id = ?, created = ? WHERE id = ?",
				managerId.getId(),
				LocalDateTime.now(),
				id.getId());
	}

	/**
	 * 削除処理
	 * @param  id 入室ID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int delete(EnterIdStatus id) {
		// 削除
		if(id == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"DELETE FROM chat_enter WHERE id = ?", 
				id.getId());
	}

	/**
	 * 全選択
	 * @return 入室モデルリスト
	 */
	@Override
	public List<EnterModel> getAll() {
		// 全選択
		
		List<EnterModel> list = new ArrayList<EnterModel>();
		try {
			String sql = "SELECT id, room_id, user_id, manager_id, max_sum, created FROM chat_enter";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
			
			for( Map<String, Object> result : resultList ) {
				EnterModel model = new EnterModel(
						new EnterIdStatus((int)result.get("id")),
						new RoomIdStatus((int)result.get("room_id")),
						new UserIdStatus((int)result.get("user_id")),
						new UserIdStatus((int)result.get("manager_id")),
						new RoomMaxNumber((int)result.get("max_sum")),
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
	 * 入室IDから選択
	 * @param  id 入室ID
	 * @return 入室モデル
	 */
	@Override
	public EnterModel select(EnterIdStatus id) {
		// IDによるデータ取得
		if(id == null)	return new EnterModel(null);
		
		EnterModel model = new EnterModel(null);
		try {
			String sql = "SELECT id, room_id, user_id, manager_id, max_sum, created FROM chat_enter WHERE id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, id.getId());
				
			model = new EnterModel(
					new EnterIdStatus((int)result.get("id")),
					new RoomIdStatus((int)result.get("room_id")),
					new UserIdStatus((int)result.get("user_id")),
					new UserIdStatus((int)result.get("manager_id")),
					new RoomMaxNumber((int)result.get("max_sum")),
					((Timestamp)result.get("created")).toLocalDateTime());
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			model = new EnterModel(null);
		}
		
		return model;
	}
	
	/**
	 * ユーザIDから入室ID選択
	 * @param userId ユーザID
	 * @return       入室ID
	 */
	@Override
	public EnterIdStatus selectId_byUserId(UserIdStatus userId) {
		// ユーザIDによるID取得
		if(userId == null) 	return new EnterIdStatus(WebConsts.ERROR_NUMBER);
		
		int id = 0;
		try {
			String sql = "SELECT id FROM chat_enter WHERE user_id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, userId.getId());
			id = (int)result.get("id");
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			id = WebConsts.ERROR_NUMBER;
		}
		return new EnterIdStatus(id);
	}

	/**
	 * 入室IDによる有無チェック
	 * @param  id 入室ID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(EnterIdStatus id) {
		// IDによる有無チェック
		if(id == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_enter WHERE id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ id.getId() },
				new int[] { Types.INTEGER },
				rs -> {
			return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
		});
	}

	/**
	 * ユーザIDによる有無チェック
	 * @param  userId ユーザID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byUserId(UserIdStatus userId) {
		// 入室しているユーザの有無の確認
		if(userId == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_enter WHERE user_id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ userId.getId() },
				new int[] { Types.INTEGER },
				rs -> {
			return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
		});
	}

	/**
	 * ルームIDによる入室数の取得
	 * @param  roomId ルームID
	 * @return ログイン数
	 */
	@Override
	public int getCount_roomId(RoomIdStatus roomId) {
		// ログインしている数の取得
		if(roomId == null)	return 0;
		
		int count = 0;
		try {
			String sql = "SELECT COUNT(*) AS COUNTER FROM chat_enter WHERE room_id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, roomId.getId());
			count = Integer.parseInt(result.get("counter").toString());
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			count = WebConsts.ERROR_NUMBER;
		}
		return count;
	}
}
