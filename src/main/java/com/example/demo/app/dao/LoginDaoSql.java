package com.example.demo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.app.entity.LoginModel;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.RoomIdStatus;
import com.example.demo.common.status.UserIdStatus;

/**
 * ログインDaoパターン
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
		this.jdbcTemp.update("INSERT INTO chat_login(room_id, user_id, created) VALUES(?,?,?)",
				model.getRoom_id(),
				model.getUser_id(),
				model.getCreated());	
	}

	/**
	 * 追加処理
	 * @param model ログインモデル
	 * return ログインID
	 */
	@Override
	public int insert_returnId(LoginModel model) {
		// 追加(return id)
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql          = "INSERT INTO chat_login(room_id, user_id, created) VALUES(?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		
       jdbcTemp.update(new PreparedStatementCreator() {
           public PreparedStatement createPreparedStatement(
               Connection connection) throws SQLException {
                   PreparedStatement ps = connection.prepareStatement(
                       sql, new String[] { "id" });
                   ps.setInt(1, model.getRoom_id());
                   ps.setInt(2, model.getUser_id());
                   ps.setTimestamp(3, timestamp);
                   return ps;
               }
           }, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 更新処理
	 * @param model ログインモデル
	 * return 0 失敗 それ以外 成功
	 */
	@Override
	public int update(LoginModel model) {
		// 更新
		return jdbcTemp.update("UPDATE chat_login SET room_id = ?, user_id = ?, created = ? WHERE id = ?",
				model.getRoom_id(),
				model.getUser_id(),
				model.getCreated(),
				model.getId());
	}
	
	/**
	 * ログインIDからルームID更新処理
	 * @param roomId ルームID
	 * @param id ログインID
	 * return 0 失敗 それ以外 成功
	 */
	@Override
	public int updateRoomId_byId(RoomIdStatus roomId, LoginIdStatus id) {
		// ルームIDの更新
		return jdbcTemp.update("UPDATE chat_login SET room_id = ? WHERE id = ?",
				roomId.getId(), 
				id.getId());
	}
	
	/**
	 * ユーザIDからルームID更新処理
	 * @param roomId ルームID
	 * @param userId ユーザID
	 * return 0 失敗 それ以外 成功
	 */
	@Override
	public int updateRoomId_byUserId(RoomIdStatus roomId, UserIdStatus userId) {
		// ユーザIDによるルームIDの更新
		return this.jdbcTemp.update("UPDATE chat_login SET room_id = ? WHERE user_id = ?",
				roomId.getId(), 
				userId.getId());
	}
	
	/**
	 * ルームIDから新規ルームIDへ更新処理
	 * @param roomId ルームID
	 * @param changeId 新規ルームID
	 * return 0 失敗 それ以外 成功
	 */
	@Override
	public int updateRoomId_byRoomId(RoomIdStatus roomId, RoomIdStatus changeId) {
		// ルームIDによるルームIDの初期化
		return this.jdbcTemp.update("UPDATE chat_login SET room_id = ? WHERE room_id = ?",
				changeId.getId(), 
				roomId.getId());
	}

	/**
	 * 削除処理
	 * @param id ログインID
	 * return 0 失敗 それ以外 成功
	 */
	@Override
	public int delete(LoginIdStatus id) {
		// 削除
		return this.jdbcTemp.update("DELETE FROM chat_login WHERE id = ?",
				id.getId());
	}

	/**
	 * 全選択
	 * return ログインモデルリスト
	 */
	@Override
	public List<LoginModel> getAll() {
		// 全選択
		String sql = "SELECT id, room_id, user_id, created FROM chat_login";
		List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
		List<LoginModel> list = new ArrayList<LoginModel>();
		
		for( Map<String, Object> result : resultList ) {
			LoginModel model = new LoginModel(
					new LoginIdStatus((int)result.get("id")),
					new RoomIdStatus((int)result.get("room_id")),
					new UserIdStatus((int)result.get("user_id")),
					((Timestamp)result.get("created")).toLocalDateTime()
					);
			list.add(model);
		}
		return list;
	}
	
	/**
	 * ルームIDから選択
	 * @param roomId ルームID
	 * return ログインモデルリスト
	 */
	@Override
	public List<LoginModel> selectList_byRoomId(RoomIdStatus roomId) {
		// ルームIDによる選択
		String sql = "SELECT id, room_id, user_id, created FROM chat_login WHERE room_id = ?";
		List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql, roomId.getId());
		List<LoginModel> list = new ArrayList<LoginModel>();
		
		for( Map<String, Object> result : resultList ) {
			LoginModel model = new LoginModel(
					new LoginIdStatus((int)result.get("id")),
					new RoomIdStatus((int)result.get("room_id")),
					new UserIdStatus((int)result.get("user_id")),
					((Timestamp)result.get("created")).toLocalDateTime());
			list.add(model);
		}
		return list;
	}

	/**
	 * ログインIDから選択
	 * @param roomId ログインID
	 * return ログインモデル
	 */
	@Override
	public LoginModel select(LoginIdStatus id) {
		// IDによるデータ取得
		String sql = "SELECT id, room_id, user_id, created FROM chat_login WHERE id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, id.getId());
			
		LoginModel model = new LoginModel(
				new LoginIdStatus((int)result.get("id")),
				new RoomIdStatus((int)result.get("room_id")),
				new UserIdStatus((int)result.get("user_id")),
				((Timestamp)result.get("created")).toLocalDateTime());
		
		return model;
	}
	
	/**
	 * ユーザIDからログインID選択
	 * @param userId ユーザID
	 * return ログインID
	 */
	@Override
	public int selectId_byUserId(UserIdStatus userId) {
		// ユーザIDによるIDの選択
		if( !this.isSelect_byUserId(userId) ) return -1;
		String sql = "SELECT id FROM chat_login WHERE user_id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, userId.getId());
		int id = (int)result.get("id");
		return id;
	}
	
	/**
	 * ユーザIDから選択
	 * @param userId ユーザID
	 * return ログインモデル
	 */
	@Override
	public LoginModel select_byuserId(UserIdStatus userId) {
		// ユーザIDによるデータ取得
		String sql = "SELECT id, room_id, user_id, created FROM chat_login WHERE user_id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, userId.getId());
			
		LoginModel model = new LoginModel(
				new LoginIdStatus((int)result.get("id")),
				new RoomIdStatus((int)result.get("room_id")),
				new UserIdStatus((int)result.get("user_id")),
				((Timestamp)result.get("created")).toLocalDateTime());
		return model;
	}
	
	/**
	 * ユーザIDからルームID選択
	 * @param userId ユーザID
	 * return ルームID
	 */
	@Override
	public int selectRoomId_byUserId(UserIdStatus userId) {
		// ユーザIDによるルームIDの選択
		if( !this.isSelect_byUserId(userId) ) return -1;
		String sql = "SELECT room_id FROM chat_login WHERE user_id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, userId.getId());
		int room_id = (int)result.get("room_id");
		return room_id;
	}

	/**
	 * ログインIDからログイン有無チェック
	 * @param id ログインID
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(LoginIdStatus id) {
		// IDによる有無チェック
		String sql = "SELECT id FROM chat_login WHERE id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ id.getId() },
				new int[] { Types.INTEGER },
				rs -> {
			return rs.next() ? true : false;	
		});
	}

	/**
	 * ユーザIDからログイン有無チェック
	 * @param userId ユーザID
	 * return true あり false なし
	 */
	@Override
	public boolean isSelect_byUserId(UserIdStatus userId) {
		// ユーザIDによるIDの有無確認
		String sql = "SELECT id FROM chat_login WHERE user_id = ?";
		return jdbcTemp.query(
				sql, 
				new Object[]{ userId.getId() },
				new int[] {	Types.INTEGER },
				rs -> {
			return rs.next() ? true : false;	
		});
	}
}
