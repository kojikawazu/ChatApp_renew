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

import com.example.demo.app.entity.UserModel;

/**
 * ユーザDaoパターン
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
	 * @param jdbcTemp
	 */
	@Autowired
	public UserDaoSql(JdbcTemplate jdbcTemp) {
		// TODO コンストラクタ
		this.jdbcTemp = jdbcTemp;
	}
	
	/**
	 * 追加処理
	 * @param model: ユーザモデル
	 */
	@Override
	public void insert(UserModel model) {
		// TODO 追加
		this.jdbcTemp.update("INSERT INTO chat_user(name, email, passwd, forgot_passwd, created, updated) VALUES(?,?,?,?,?,?)",
				model.getName(),
				model.getEmail(),
				model.getPasswd(),
				model.getForgot_passwd(),
				model.getCreated(),
				model.getUpdated());
	}

	/**
	 * 追加処理
	 * @param model: ユーザモデル
	 * return ユーザID
	 */
	@Override
	public int insert_byId(UserModel model) {
		// TODO 追加(return id)
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO chat_user(name, email, passwd, forgot_passwd, created, updated) VALUES(?,?,?,?,?,?)";
		Timestamp timestamp = Timestamp.valueOf(model.getCreated());
		Timestamp timestamp2 = Timestamp.valueOf(model.getUpdated());
		
       jdbcTemp.update(new PreparedStatementCreator() {
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
		return keyHolder.getKey().intValue();
	}

	/**
	 * 更新処理
	 * @param model: ユーザモデル
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int update(UserModel model) {
		// TODO 更新
		return jdbcTemp.update("UPDATE chat_user SET name = ?, email = ?, passwd = ?, forgot_passwd = ?, created = ?, updated = ? WHERE id = ?",
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
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * @param passwd: パスワード
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int update_passwd(String name, String email, String passwd) {
		// TODO パスワード更新
		return jdbcTemp.update("UPDATE chat_user SET passwd = ?, updated = ? WHERE name = ? AND email = ?",
				passwd, LocalDateTime.now(), name, email);
	}

	/**
	 * 削除処理
	 * @param id: ユーザID
	 * return 0: 失敗 それ以外: 成功
	 */
	@Override
	public int delete(int id) {
		// TODO 削除
		return this.jdbcTemp.update("DELETE FROM chat_user WHERE id = ?", id);
	}

	/**
	 * 全て選択
	 * return ユーザモデルリスト
	 */
	@Override
	public List<UserModel> getAll() {
		// TODO 全選択
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user";
		List<Map<String, Object>> resultList = jdbcTemp.queryForList(sql);
		List<UserModel> list = new ArrayList<UserModel>();
		
		for( Map<String, Object> result : resultList ) {
			UserModel model = new UserModel();
			model.setId((int)result.get("id"));
			model.setName((String)result.get("name"));
			model.setEmail((String)result.get("email"));
			model.setPasswd((String)result.get("passwd"));
			model.setForgot_passwd((String)result.get("forgot_passwd"));
			model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			model.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
			list.add(model);
		}
		return list;
	}

	/**
	 * ユーザIDによる選択
	 * @param: id: ユーザID
	 * return ユーザモデル
	 */
	@Override
	public UserModel select(int id) {
		// TODO IDによるデータ取得
		String sql = "SELECT id, name, email, passwd, forgot_passwd, created, updated FROM chat_user WHERE id = ?";
		Map<String, Object> result = jdbcTemp.queryForMap(sql, id);
		
		UserModel model = new UserModel();
		model.setId((int)result.get("id"));
		model.setName((String)result.get("name"));
		model.setEmail((String)result.get("email"));
		model.setPasswd((String)result.get("passwd"));
		model.setForgot_passwd((String)result.get("forgot_passwd"));
		model.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		model.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
		
		return model;
	}
	
	/**
	 * ユーザ名、Eメール、パスワードによるユーザID選択
	 * @param: name: ユーザ名
	 * @param: email: Eメール
	 * @param: passwd: パスワード
	 * return ユーザID
	 */
	@Override
	public int selectId_byNameEmailPass(String name, String email, String passwd) {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ? AND passwd = ?";
		return jdbcTemp.query(sql, new Object[]{ name, email, passwd }, rs -> {
			return rs.next() ? (int)rs.getInt("id") : -1;	
		});
	}

	/**
	 * ユーザIDによる有無チェック
	 * @param id: ユーザID
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byId(int id) {
		// TODO IDによる有無チェック
		String sql = "SELECT id FROM chat_user WHERE id = ?";
		return jdbcTemp.query(sql, new Object[]{ id }, rs -> {
			return rs.next() ? true : false;	
		});
	}

	/**
	 * ユーザ名、Eメールによる有無チェック
	 * @param name: ユーザID
	 * @param email: Eメール
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byNameEmail(String name, String email) {
		// TODO 名前とメールアドレスの一致による有無確認
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ?";
		return jdbcTemp.query(sql, new Object[]{ name, email }, rs -> {
			return rs.next() ? true : false;	
		});
	}

	/**
	 * ユーザ名、Eメール、忘れたとき用パスワードによる有無チェック
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * @param forgot: 忘れたとき用パスワード
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byNameEmailForgotPW(String name, String email, String forgot) {
		// TODO 名前とメールアドレス忘れた用パスワードの一致確認
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ? AND forgot_passwd = ?";
		return jdbcTemp.query(sql, new Object[]{ name, email, forgot }, rs -> {
			return rs.next() ? true : false;	
		});
	}

	/**
	 * ユーザ名、Eメール、パスワードによる有無チェック
	 * @param name: ユーザ名
	 * @param email: Eメール
	 * @param passwd: パスワード
	 * return true: あり false: なし
	 */
	@Override
	public boolean isSelect_byNameEmailPass(String name, String email, String passwd) {
		// TODO 名前とメールアドレス,パスワードの一致確認
		String sql = "SELECT id FROM chat_user WHERE name = ? AND email = ? AND passwd = ?";
		return jdbcTemp.query(sql, new Object[]{ name, email, passwd }, rs -> {
			return rs.next() ? true : false;	
		});
	}

}
