package com.example.demo.common.status;

import com.example.demo.app.config.WebConsts;

/**
 * コメントIDのステータス受け渡しクラス
 */
public class CommentIdStatus implements SuperStatus {

	/**
	 * フィールド
	 * 
	 */
	private int commentId;		/** コメントID */

	/**
	 * コンストラクタ
	 * @param commentId コメントID
	 */
	public CommentIdStatus(int commentId) {
		this.commentId = commentId;
	}
	
	/**
	 * エラーか判定する
	 * @return True エラー False エラーでない
	 */
	@Override
	public boolean isError() {
		return ( this.commentId == WebConsts.ERROR_NUMBER );
	}

	/**
	 * IDの取得
	 * @return ID コメントID
	 */
	@Override
	public int getId() {
		return this.commentId;
	}
}
