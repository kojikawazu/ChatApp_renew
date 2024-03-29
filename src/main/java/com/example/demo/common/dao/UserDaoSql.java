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
import com.example.demo.app.entity.UserModel;
import com.example.demo.common.status.LoginIdStatus;
import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.PasswordWord;
import com.example.demo.common.word.UserNameEmail;
import com.example.demo.common.word.UserNameEmailPassword;

/**
 * ユーザDaoパターン
 * @author nanai 
 *
 */
@Repository
public class UserDaoSql implements UserDao {

	/**
	 * Field
	 */
	private JdbcTemplate jdbcTemp;
	
	/**
	 * コンストラクタ
	 * @param jdbcTemp jdbcドライバー
	 */
	@Autowired
	public UserDaoSql(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model ユーザモデル
	 */
	@Override
	public void insert(UserModel model) {
		// 追加
		if (model == null)	return;
		
		try {
			this.jdbcTemp.update(
					"INSERT INTO chat_user(name, email, passwd, forgot_passwd, created, updated) VALUES(?,?,?,?,?,?)",
					model.getName(),
					model.getEmail(),
					model.getPasswd(),
					model.getForgot_passwd(),
					model.getCreated(),
					model.getUpdated());	
		} catch(DataAccessException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 追加処理
	 * @param  model ユーザモデル
	 * @return ユーザーID 失敗した場合は-1。
	 * 
	 */
	@Override
	public UserIdStatus insert_returnId(UserModel model) {
		// 追加(return id)
		if(model == null) return new UserIdStatus(WebConsts.ERROR_NUMBER);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO chat_user(name, email, passwd, forgot_passwd, created, updated) VALUES(?,?,?,?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		Timestamp timestamp2 = Timestamp.valueOf(model.getUpdated());
		int return_key = 0;

		try {
			this.jdbcTemp.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
					       sql, new String[] { "id" });
					   ps.setString(1, model.getName());
					   ps.setString(2, model.getEmail());
					   ps.setString(3, model.getPasswd());
					   ps.setString(4, model.getForgot_passwd());
					   ps.setTimestamp(5, timestamp);
					   ps.setTimestamp(6, timestamp2);
					   return ps;
					}
				}, keyHolder);
			return_key = keyHolder.getKey().intValue();
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			return_key = WebConsts.ERROR_NUMBER;
		}
       
		return new UserIdStatus(return_key);
	}

	/**
	 * 更新処理
	 * @param  model ユーザモデル
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int update(UserModel model) {
		// 更新
		if(model == null)	return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"UPDATE chat_user SET name = ?, email = ?, passwd = ?, forgot_passwd = ?, created = ?, updated = ? WHERE id = ?",
				model.getName(),
				model.getEmail(),
				model.getPasswd(),
				model.getForgot_passwd(),
				model.getCreated(),
				model.getUpdated(),
				model.getId());
	}
	
	/**
	 * パスワード更新処理
	 * @param  user ユーザ名, Eメール, パスワードクラス
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int update_passwd(UserNameEmailPassword user) {
		// パスワード更新
		if(user == null) return WebConsts.ERROR_NUMBER;
		
		return jdbcTemp.update("UPDATE chat_user SET passwd = ?, updated = ? WHERE name = ? AND email = ?",
				user.getPassword(), 
				LocalDateTime.now(),
				user.getName(),
				user.getEmail());
	}

	/**
	 * 削除処理
	 * @param  id ユーザID
	 * @return 0以下 失敗 それ以外 成功
	 */
	@Override
	public int delete(UserIdStatus id) {
		// 削除
		if(id == null) return WebConsts.ERROR_NUMBER;
		
		return this.jdbcTemp.update(
				"DELETE FROM chat_user WHERE id = ?",
				id.getId());
	}

	/**
	 * 全て選択
	 * @return ユーザモデルリスト
	 */
	@Override
	public List<UserModel> getAll() {
		// 全選択
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user";
			List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
			
			for( Map<String, Object> result : resultList ) {
				UserModel model = new UserModel(
						new UserIdStatus((int)result.get("id")),
						new NameWord((String)result.get("name")),
						new EmailWord((String)result.get("email")),
						new PasswordWord((String)result.get("passwd")),
						new PasswordWord((String)result.get("forgot_passwd")),
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
	 * ユーザIDによる選択
	 * @param id ユーザID
	 * @return   ユーザモデル
	 */
	@Override
	public UserModel select(UserIdStatus id) {
		// IDによるデータ取得
		if(id == null)	return null;
		
		UserModel model = null;
		try {
			String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user WHERE id = ?";
			Map<String, Object> result = jdbcTemp.queryForMap(sql, id.getId());
			
			model = new UserModel(
					new UserIdStatus((int)result.get("id")),
					new NameWord((String)result.get("name")),
					new EmailWord((String)result.get("email")),
					new PasswordWord((String)result.get("passwd")),
					new PasswordWord((String)result.get("forgot_passwd")),
					((Timestamp)result.get("created")).toLocalDateTime(),
					((Timestamp)result.get("updated")).toLocalDateTime());
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			model = null;
		}
		
		return model;
	}
	
	/**
	 * LoginIdを元にユーザーモデルを選択する
	 * @param ログインID
	 * @return ユーザーモデル
	 */
	@Override
	public UserModel select_byId_subLoginId(LoginIdStatus loginId) {
		if(loginId == null)	return null;
		
		UserModel model = null;
		try {
			String sql = "SELECT * FROM chat_user WHERE id in"
					+ "("
					+ "SELECT user_id FROM chat_login WHERE id = ?"
					+ ")";
			Map<String, Object> result = this.jdbcTemp.queryForMap(sql, loginId.getId());
			model = new UserModel(
					new UserIdStatus((int)result.get("id")),
					new NameWord((String)result.get("name")),
					new EmailWord((String)result.get("email")),
					new PasswordWord((String)result.get("passwd")),
					new PasswordWord((String)result.get("forgot_passwd")),
					((Timestamp)result.get("created")).toLocalDateTime(),
					((Timestamp)result.get("updated")).toLocalDateTime());
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			model = null;
		}
		
		return model;
	}
	
	/**
	 * ユーザ名、Eメール、パスワードによるユーザID選択
	 * @param user ユーザ名, Eメール, パスワードクラス
	 * @return     ユーザID
	 */
	@Override
	public UserIdStatus selectId_byNameEmailPass(UserNameEmailPassword user) {
		// ユーザ名、Eメール、パスワードによるユーザID選択
		if(user == null)	return null;
		
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ? AND passwd = ?";
		UserIdStatus userId = null;
		try {
			userId = this.jdbcTemp.query(
					sql, 
					new Object[]{ user.getName(), user.getEmail(), user.getPassword() }, 
					new int[] {	Types.VARCHAR, Types.VARCHAR, Types.VARCHAR },
					rs -> {
						UserIdStatus status = new UserIdStatus(rs.next() ? (int)rs.getInt("id") : -1);
						return status;
					}
			);
		} catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			userId = null;
		}
		
		return userId;
	}

	/**
	 * ユーザIDによる有無チェック
	 * @param  id ユーザID
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byId(UserIdStatus id) {
		// IDによる有無チェック
		if(id == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_user WHERE id = ?";
		return this.jdbcTemp.query(
				sql, 
				new Object[]{ id.getId() },
				new int[] {	Types.INTEGER },
				rs -> {
					return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
				}
		);
	}

	/**
	 * ユーザ名、Eメールによる有無チェック
	 * @param  user ユーザ名, Eメールクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmail(UserNameEmail user) {
		// 名前とメールアドレスの一致による有無確認
		if(user == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ?";
		return this.jdbcTemp.query(
				sql,
				new Object[]{ user.getName(), user.getEmail() }, 
				new int[] {	Types.VARCHAR, Types.VARCHAR },
				rs -> {
					return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
				}
		);
	}
	
	/**
	 * ユーザ名またはEメールによる有無チェック
	 * @param  user ユーザ名, Eメールクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameOrEmail(UserNameEmail user) {
		// 名前とメールアドレスの一致による有無確認
		if(user == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_user WHERE name = ? OR email = ?";
		return this.jdbcTemp.query(
				sql,
				new Object[]{ user.getName(), user.getEmail() }, 
				new int[] {	Types.VARCHAR, Types.VARCHAR },
				rs -> {
					return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
				}
		);
	}

	/**
	 * ユーザ名、Eメール、忘れたとき用パスワードによる有無チェック
	 * @param  user ユーザ名, Eメール, パスワードクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmailForgotPW(UserNameEmailPassword user) {
		// 名前とメールアドレス忘れた用パスワードの一致確認
		if(user == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ? AND forgot_passwd = ?";
		return this.jdbcTemp.query(
				sql, 
				new Object[]{ user.getName(), user.getEmail(), user.getPassword() },
				new int[] {	Types.VARCHAR, Types.VARCHAR, Types.VARCHAR },
				rs -> {
					return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
				}
		);
	}

	/**
	 * ユーザ名、Eメール、パスワードによる有無チェック
	 * @param  user ユーザ名, Eメール, パスワードクラス
	 * @return true あり false なし
	 */
	@Override
	public boolean isSelect_byNameEmailPass(UserNameEmailPassword user) {
		// 名前とメールアドレス,パスワードの一致確認
		if(user == null)	return WebConsts.DB_ENTITY_NONE;
		
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ? AND passwd = ?";
		return this.jdbcTemp.query(
				sql, 
				new Object[]{ user.getName(), user.getEmail(), user.getPassword() },
				new int[] {	Types.VARCHAR, Types.VARCHAR, Types.VARCHAR },
				rs -> {
					return rs.next() ? WebConsts.DB_ENTITY_FINDED : WebConsts.DB_ENTITY_NONE;
				}
		);
	}
}
