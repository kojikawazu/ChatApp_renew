package com.example.demo.app.entity;

import java.time.LocalDateTime;

import com.example.demo.common.status.UserIdStatus;
import com.example.demo.common.word.EmailWord;
import com.example.demo.common.word.NameWord;
import com.example.demo.common.word.PasswordWord;

/**
 * ユーザモデル
 *
 */
public class UserModel {
	
	private UserIdStatus id;			/** ユーザID */
	private NameWord name;				/** ユーザ名 */
	private EmailWord email;			/** Eメール  */
	private PasswordWord passwd;		/** パスワード */
	private PasswordWord forgot_passwd;	/** 忘れたとき用パスワード */
	private LocalDateTime created;		/** 生成日時 */
	private LocalDateTime updated;		/** 更新日時 */
	
	/**
	 * コンストラクタ
	 * @param model: ログインモデルクラス
	 */
	public UserModel(UserModel model) {
		super();

		if( model == null ) {
			this.id 			= new UserIdStatus(0);
			this.name 			= new NameWord("");
			this.email 			= new EmailWord("");
			this.passwd 		= new PasswordWord("");
			this.forgot_passwd 	= new PasswordWord("");
			this.created 		= LocalDateTime.now();
			this.updated 		= LocalDateTime.now();
		} else {
			this.id 			= new UserIdStatus(model.getId());
			this.name 			= new NameWord(model.getName());
			this.email 			= new EmailWord(model.getEmail());
			this.passwd 		= new PasswordWord(model.getPasswd());
			this.forgot_passwd 	= new PasswordWord(model.getForgot_passwd());
			this.created 		= model.getCreated();
			this.updated 		= model.getUpdated();
		}
	}
	
	/**
	 * コンストラクタ
	 * @param id ID
	 * @param name 名前
	 * @param email　Eメールアドレス
	 * @param passwd パスワード
	 * @param forgot_passwd 忘れた時用パスワード
	 * @param created 生成日時
	 * @param updated 更新日時
	 */
	public UserModel(
			UserIdStatus id,
			NameWord name,
			EmailWord email,
			PasswordWord passwd,
			PasswordWord forgot_passwd,
			LocalDateTime created,
			LocalDateTime updated) {
		super();
		
		this.id = (id == null ? 
				new UserIdStatus(0) : 
				new UserIdStatus(id.getId()));
		
		this.name = (name == null ?
				new NameWord("") :
				new NameWord(name.getString()));
		
		this.email = (email == null ?
				new EmailWord("") :
				new EmailWord(email.getString()));
		
		this.passwd = (passwd == null ?
				new PasswordWord("") :
				new PasswordWord(passwd.getString()));
		
		this.forgot_passwd = (forgot_passwd == null ?
				new PasswordWord("") :
				new PasswordWord(forgot_passwd.getString()));
		
		this.created = (created == null ?
				LocalDateTime.now() :
				created);
		
		this.updated = (updated == null ?
				LocalDateTime.now() :
				updated);
	}

	public int getId() {
		return id.getId();
	}

	protected void setId(int id) {
		this.id = new UserIdStatus(id);
	}

	public String getName() {
		return name.getString();
	}

	protected void setName(String name) {
		this.name = new NameWord(name);
	}

	public String getEmail() {
		return email.getString();
	}

	protected void setEmail(String email) {
		this.email = new EmailWord(email);
	}

	public String getPasswd() {
		return passwd.getString();
	}

	protected void setPasswd(String passwd) {
		this.passwd = new PasswordWord(passwd);
	}

	public String getForgot_passwd() {
		return forgot_passwd.getString();
	}

	protected void setForgot_passwd(String forgot_passwd) {
		this.forgot_passwd = new PasswordWord(forgot_passwd);
	}

	public LocalDateTime getCreated() {
		return created;
	}

	protected void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	protected void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
}
