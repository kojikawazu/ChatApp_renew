package com.example.demo.common.log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * ログクラス(スーパークラス)
 * @author nanai
 *
 */
public class AppLogger {
	
	/** 固定 */
	
	/** ログ名 */
	private final String logName = "ChatApp_Log";
	
	/** Linux ディレクトリ */
	private final String LINUX_APP_LOG_DIR = "/var/tomcat/chatapp";
	
	/** ---------------------------------------------------------------------------------- */
	
	/** ログクラス */
	private Logger logger = null;
	
	
	/** ログファイル名 */
	private String SuperlogFileName = "AppLogger.log";
	
	/** ログファイルフルパス */
	private String SuperLogFileFullPath = "";
	
	/** OS名 */
	private String os_name = "windows";
	
	/**
	 * コンストラクタ
	 * @param ログファイル名
	 */
	protected AppLogger(String logFileName) {
		this.initLog_FileConsole(logFileName);
	}
	
	/**
	 * ログの初期化
	 */
	protected void initLog_FileConsole(String logFileName) {
		this.SuperlogFileName = logFileName;
		
		if( this.logger == null ) {
			this.logger = Logger.getLogger(this.logName);
			
			try {
				// ファイル出力の設定
				this.os_name = System.getProperty("os.name").toLowerCase();
				if( this.os_name.indexOf("windows") != -1 ) {
					// windows
					this.SuperLogFileFullPath = System.getProperty("user.dir");
					this.SuperLogFileFullPath = new File(this.SuperLogFileFullPath, this.SuperlogFileName).getPath();
				} else {
					// Linux
					Path directoryPath = Paths.get(this.LINUX_APP_LOG_DIR);
					// ディレクトリチェック
					if( !Files.isDirectory(directoryPath) ) {
						// ディレクトリ作成
						Files.createDirectories(directoryPath);
					}
					this.SuperLogFileFullPath = this.LINUX_APP_LOG_DIR + "/" + this.SuperlogFileName;
				}
				
				System.out.println("Create Log File Path: " + this.SuperLogFileFullPath);
				Handler handler = new FileHandler(this.SuperLogFileFullPath);
				handler.setFormatter(new SimpleFormatter());
				
				this.logger.addHandler(handler);
				this.logger.setLevel(Level.ALL);
				
				// 標準出力の設定
				ConsoleHandler consoleHandler = new ConsoleHandler();
				consoleHandler.setLevel(Level.ALL);
				this.logger.addHandler(consoleHandler);

			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 呼び出し元クラス名メソッド名を取得
	 * @return メソッド名(クラス名:呼び出し行番号)
	 */
	private String calledFrom() {
		StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
		if (steArray.length <= 3) {
		    return "";
		}
		StackTraceElement ste = steArray[3];
		StringBuilder sb = new StringBuilder();
		/** メソッド名取得*/
		sb.append(ste.getMethodName())
			.append("(")
			/** ファイル名取得 */
			.append(ste.getFileName())
			.append(":")
			/** 行番号取得 */
			.append(ste.getLineNumber())
			.append(")");
		return sb.toString();
    }
	
	/**
	 * 準備チェック
	 * @return true 準備OK false 準備できてない
	 */
	private boolean isSetting() {
		if(this.logger == null) {
			System.out.println("Please set logger");
			return false;
		}
		return true;
	}
	
	/**
	 * 開始ログ
	 * @param log
	 */
	public void start(String log) {
		if(!this.isSetting())	return ;
		String calledFromString = this.calledFrom();
		this.logger.info(calledFromString + ":[start]" + log);
	}
	
	/**
	 * 失敗ログ
	 * @param log
	 */
	public void error(String log) {
		if(!this.isSetting())	return ;
		String calledFromString = this.calledFrom();
		this.logger.log(Level.CONFIG, calledFromString + ":[FAILED]" + log);
	}
	
	/**
	 * 成功ログ 
	 * @param log
	 */
	public void successed(String log) {
		if(!this.isSetting())	return ;
		String calledFromString = this.calledFrom();
		this.logger.info(calledFromString + ":[successed]" + log);
	}
	
	/**
	 * 通常ログ
	 * @param log
	 */
	public void info(String log) {
		if(!this.isSetting())	return ;
		String calledFromString = this.calledFrom();
		this.logger.info(calledFromString + log);
	}
}
