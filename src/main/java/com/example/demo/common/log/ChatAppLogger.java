package com.example.demo.common.log;

/**
 * チャットアプリログクラス
 * @author nanai
 *
 */
public class ChatAppLogger extends AppLogger {

	/** シングルトン */
	private static ChatAppLogger app = null;
	
	/** ログファイル名 */
	private static final String logFileName = "Chat__.log";
	
	/**
	 * コンストラクタ
	 * @param logFileName ログファイル名
	 */
	private ChatAppLogger(String logFileName) {
		super(logFileName);
	}
	
	/**
	 * クラス取得
	 * @return ChatAppLoggerクラスインスタンス
	 */
	public static ChatAppLogger getInstance() {
		if( app == null ) {
			app = new ChatAppLogger(logFileName);
		}
		return app;
	}
}
