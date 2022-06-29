package com.example.demo.app.config;

import com.example.demo.app.status.SuperStatus;

/**
 * 共通メソッド群
 * @author nanai
 *
 */
public class WebFunctions {

	/**
	 * nullでないか判定
	 * @param status: ID取得クラス
	 * @return True: nullでない False: null
	 */
	public static final boolean isNotNull(SuperStatus status) {
		return ( status != null );
	}
}
