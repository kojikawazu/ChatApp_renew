package com.example.demo.common.session;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * ログインIDセッションクラス
 * @author nanai
 *
 */
@Component
@SessionScope
public class SessionLoginId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ログインID(文字列)
	 */
	private String loginId = "";
	
	/**
	 * ログインIDをブランクへ変更
	 */
	public void setBlank() {
		this.loginId = "";
	}
	
	public String getLoginId() {
		return this.loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
