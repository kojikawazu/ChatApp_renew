package com.example.demo.app.form;

/**
 * フォームインターフェース
 * @author nanai
 *
 */
public interface SuperForm {
	
	/** 小文字英字 or 大文字英字 or 数値の正規表現 */
	public static String MATCH_SMALL_BIG_NUMBER = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";
	
	public static String ERROR_MATCH_SMALL_BIG_NUMBER_MESSAGE = "小文字英字と大文字英字と数字を含めたパスワードにしてください。";
	

}
