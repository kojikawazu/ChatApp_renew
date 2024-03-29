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
import com.example.demo.app.entity.LoginModel;
import com.example.demo.app.entity.LoginModelEx;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.NameWord;

/**
 * ログインDaoパターン
 * @author nanai
 *
 */
@Repository
public class LoginDaoSql implements LoginDao {

	/**
	 * Field
	 */
	private JdbcTemplate jdbcTemp;
	
	/**
	 * コンストラクタ
	 * @param jdbcTemp jdbcドライバー
	 */
	@Autowired
	public LoginDaoSql(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model ログインモデル
	 */
	@Override
	public void insert(LoginModel model) {
		// 追加
		if(model == null)	return;
		
		try {
			this.jdbcTemp.update(
					"INSERT INTO "
					+ "chat_login(room_id, user_id, created, updated) "
					+ "VALUES(?,?,?,?)",
					model.getRoom_id(),
					model.getUser_id(),
					model.getCreated(),
					model.getUpdated());
		} catch(DataAccessException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 追加処理
	 * @param  model ログインモデル
	 * @return ログインID 失敗した場合は-1。
	 */
	@Override
	public LoginIdStatus insert_returnId(LoginModel model) {
		// 追加(return id)
		if(model == null)	return new LoginIdStatus(WebConsts.ERROR_NUMBER);
		
		KeyHolder keyHolder        = new GeneratedKeyHolder();
		String sql                 = "INSERT INTO "
									+ "chat_login(room_id, user_id, created, updated) "
									+ "VALUES(?,?,?,?)";
		Timestamp timestamp        = Timestamp.valueOf(model.getCreated());
		Timestamp timestampUpdated = Timestamp.valueOf(model.getUpdated());
		int return_key = 0;
		
		try {
			jdbcTemp.update(new PreparedStatementCreator() {
			   public PreparedStatement createPreparedStatement(
			       Connection connection) throws SQLException {
			           PreparedStatement ps = connection.prepareStatement(
			               sql, new String[] { "id" });
			           ps.setInt(1, model.getRoom_id());
			           ps.setInt(2, model.getUser_id());
			           ps.setTimestamp(3, timestamp);
			           ps.setTimestamp(4, timestampUpdated);
			           return ps;
			       }
			   }, keyHolder);
			return_key = keyHolder.getKey().intValue();
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			return_key = WebConsts.ERROR_NUMBER;
		}
		return new LoginIdStatus(return_key);
	}

	/**
	 * 更新処理
	 * @param model ログインモデル
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int update(LoginModel model) {
		// 更新
		if(model == null)	return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_login SET "
				+ "room_id = ?, user_id = ?, created = ?, updated = ? "
				+ "WHERE id = ?",
				model.getRoom_id(),
				model.getUser_id(),
				model.getCreated(),
				model.getUpdated(),
				model.getId());
	}
	
	/**
	 * ログインIDからルームID更新処理
	 * @param roomId  ルームID
	 * @param id      ログインID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int updateRoomId_byId(RoomIdStatus roomId, LoginIdStatus id) {
		// ルームIDの更新
		if(roomId == null || id == null)	return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update(
				"UPDATE chat_login SET "
				+ "room_id = ? "
				+ "WHERE id = ?",
				roomId.getId(),
				id.getId());
	}
	
	/**
	 * ユーザIDからルームID更新処理
	 * @param roomId  ルームID
	 * @param userId  ユーザID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int updateRoomId_byUserId(RoomIdStatus roomId, UserIdStatus userId) {
		// ユーザIDによるルームIDの更新
		if(roomId == null || userId == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"UPDATE chat_login SET "
				+ "room_id = ? "
				+ "WHERE user_id = ?",
				roomId.getId(),
				userId.getId());
	}
	
	/**
	 * ルームIDから新規ルームIDへ更新処理
	 * @param roomId   ルームID
	 * @param changeId 新規ルームID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int updateRoomId_byRoomId(RoomIdStatus roomId, RoomIdStatus changeId) {
		// ルームIDによるルームIDの初期化
		if(roomId == null || changeId == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"UPDATE chat_login SET "
				+ "room_id = ? "
				+ "WHERE room_id = ?",
				changeId.getId(),
				roomId.getId());
	}
	
	/**
	 * 更新日付だけの更新
	 * @param updated 更新日付
	 * @param id      ログインID
	 * @return        0以下 失敗 それ以外 成功
	 */
	@Override
	public int updateUpdated_byId(LocalDateTime updated, LoginIdStatus id) {
		// ログインIDによる更新日付の更新
		
		if(updated == null || id == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"UPDATE chat_login SET "
				+ "updated = ? "
				+ "WHERE id = ?",
				updated,
				id.getId());
	}

	/**
	 * 削除処理
	 * @param  id ログインID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int delete(LoginIdStatus id) {
		// 削除
		if(id == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"DELETE FROM chat_login "
				+ "WHERE id = ?",
				id.getId());
	}

	/**
	 * 全選択
	 * @return ログインモデルリスト
	 */
	@Override
	public List<LoginModel> getAll() {
		// 全選択
		List<LoginModel> list = new ArrayList<LoginModel>();
		try {
			String sql = "SELECT * "
					+ "FROM chat_login";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
			
			for( Map<String, Object> result : resultList ) {
				LoginModel model = new LoginModel(
						new LoginIdStatus((int)result.get("id")),
						new RoomIdStatus((int)result.get("room_id")),
						new UserIdStatus((int)result.get("user_id")),
						((Timestamp)result.get("created")).toLocalDateTime(),
						((Timestamp)result.get("updated")).toLocalDateTime()
						);
				list.add(model);
			}
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			list.clear();
		}
		return list;
	}
	
	/**
	 * ルームIDから選択
	 * @param roomId ルームID
	 * @return ログインモデルリスト
	 */
	@Override
	public List<LoginModel> selectList_byRoomId(RoomIdStatus roomId) {
		// ルームIDによる選択
		List<LoginModel> list = new ArrayList<LoginModel>();
		try {
			String sql = "SELECT * "
					+ "FROM chat_login "
					+ "WHERE room_id = ?";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql, roomId.getId());
			
			for( Map<String, Object> result : resultList ) {
				LoginModel model = new LoginModel(
						new LoginIdStatus((int)result.get("id")),
						new RoomIdStatus((int)result.get("room_id")),
						new UserIdStatus((int)result.get("user_id")),
						((Timestamp)result.get("created")).toLocalDateTime(),
						((Timestamp)result.get("updated")).toLocalDateTime()
						);
				list.add(model);
			}
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			list.clear();
		}
		return list;
	}
	
	/**
	 * ルームIDによる選択 + ユーザ名選択
	 * @param ルームID
	 * @return ログインモデル拡張版リスト
	 */
	@Override
	public List<LoginModelEx> selectList_plusUserName_byRoomId(RoomIdStatus roomId) {
		// ルームIDによる選択 + ユーザー名選択
		List<LoginModelEx> list = new ArrayList<LoginModelEx>();
		try {
			String sql = "SELECT chat_login.*, chat_user.name AS user_name "
					+ "FROM chat_login LEFT OUTER JOIN chat_user ON "
					+ "chat_login.user_id = chat_user.id "
					+ "WHERE room_id = ?";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql, roomId.getId());
			
			for( Map<String, Object> result : resultList ) {
				LoginModel superModel = new LoginModel(
						new LoginIdStatus((int)result.get("id")),
						new RoomIdStatus((int)result.get("room_id")),
						new UserIdStatus((int)result.get("user_id")),
						((Timestamp)result.get("created")).toLocalDateTime(),
						((Timestamp)result.get("updated")).toLocalDateTime()
						);
				
				LoginModelEx model = new LoginModelEx(
						superModel,
						new NameWord((String)result.get("user_name")));
				
				list.add(model);
			}
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			list.clear();
		}
		return list;
	}

	/**
	 * ログインIDから選択
	 * @param roomId ログインID
	 * @return ログインモデル
	 */
	@Override
	public LoginModel select(LoginIdStatus id) {
		// IDによるデータ取得
		LoginModel model = null;
		try {
			String sql = "SELECT * "
					+ "FROM chat_login "
					+ "WHERE id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, id.getId());
			
			model = new LoginModel(
					new LoginIdStatus((int)result.get("id")),
					new RoomIdStatus((int)result.get("room_id")),
					new UserIdStatus((int)result.get("user_id")),
					((Timestamp)result.get("created")).toLocalDateTime(),
					((Timestamp)result.get("updated")).toLocalDateTime()
					);
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			model = null;
		}
		return model;
	}
	
	/**
	 * ユーザIDからログインID選択
	 * @param userId ユーザID
	 * @return ログインID
	 */
	@Override
	public LoginIdStatus selectId_byUserId(UserIdStatus userId) {
		// ユーザIDによるIDの選択
		if( !this.isSelect_byUserId(userId) ) return null;
		int id = 0;
		try {
			String sql = "SELECT id FROM chat_login WHERE user_id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, userId.getId());
			id = (int)result.get("id");
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			id = WebConsts.ERROR_NUMBER;
			return null;
		}
		return new LoginIdStatus(id);
	}
	
	/**
	 * ユーザIDから選択
	 * @param userId ユーザID
	 * @return ログインモデル
	 */
	@Override
	public LoginModel select_byuserId(UserIdStatus userId) {
		// ユーザIDによるデータ取得
		if( !this.isSelect_byUserId(userId) ) return null;
		
		LoginModel model = new LoginModel(null);
		try {
			String sql = "SELECT * "
					+ "FROM chat_login "
					+ "WHERE user_id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, userId.getId());
				
			model = new LoginModel(
					new LoginIdStatus((int)result.get("id")),
					new RoomIdStatus((int)result.get("room_id")),
					new UserIdStatus((int)result.get("user_id")),
					((Timestamp)result.get("created")).toLocalDateTime(),
					((Timestamp)result.get("updated")).toLocalDateTime()
					);
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
		return model;
	}
	
	/**
	 * ユーザIDからルームID選択
	 * @param userId ユーザID
	 * @return ルームID
	 */
	@Override
	public RoomIdStatus selectRoomId_byUserId(UserIdStatus userId) {
		// ユーザIDによるルームIDの選択
		if( !this.isSelect_byUserId(userId) ) return null;
		
		int room_id = 0;
		try {
			String sql = "SELECT room_id "
					+ "FROM chat_login "
					+ "WHERE user_id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, userId.getId());
			room_id = (int)result.get("room_id");
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
		return new RoomIdStatus(room_id);
	}

	/**
	 * ログインIDからログイン有無チェック
	 * @param id ログインID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(LoginIdStatus id) {
		// IDによる有無チェック
		if(id == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id "
				+ "FROM chat_login "
				+ "WHERE id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ id.getId() },
				new int[] { Types.INTEGER },
				rs -> {
			return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
		});
	}

	/**
	 * ユーザIDからログイン有無チェック
	 * @param userId ユーザID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byUserId(UserIdStatus userId) {
		// ユーザIDによるIDの有無確認
		if(userId == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id "
				+ "FROM chat_login "
				+ "WHERE user_id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ userId.getId() },
				new int[] {	Types.INTEGER },
				rs -> {
			return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
		});
	}
}
