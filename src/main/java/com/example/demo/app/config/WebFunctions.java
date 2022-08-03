package com.example.demo.app.config;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.example.demo.common.status.SuperStatus;

/**
 * 共通メソッド群
 * @author nanai
 *
 */
public class WebFunctions {

	/**
	 * nullでないか判定
	 * @param status ID取得クラス
	 * @return True nullでない False null
	 */
	public static final boolean isNotNull(SuperStatus status) {
		return ( status != null );
	}
	
	/**
	 * 時間、日付のdiffチェック(分単位)
	 * @param targetTime     チェック日付対象
	 * @param minutes        分単位
	 * @return  true 日付過ぎてる false 日付過ぎてない
	 */
	public static final boolean checkDiffMinutes(LocalDateTime targetTime, int minutes) {
		LocalDateTime now       = LocalDateTime.now();
		
		Long diffYears    =  ChronoUnit.YEARS.between(targetTime, now);
		Long diffMonth    =  ChronoUnit.MONTHS.between(targetTime, now);
		Long diffDay      =  ChronoUnit.DAYS.between(targetTime, now);
		Long diffHour     =  ChronoUnit.HOURS.between(targetTime, now);
		Long diffMinute   =  ChronoUnit.MINUTES.between(targetTime, now);
		
		System.out.println(
				"OK [" + diffYears + "-" + diffMonth + "-" + diffDay + " " 
				+ diffHour + ":" + diffMinute + "]");
		
		// 日付、時間が過ぎてたらアウト
		if(diffYears > 0 || diffMonth > 0 || diffDay > 0 || diffHour > 0) {
			return WebConsts.TIME_RUNNING_OUT;
		}
		
		// [minutes]分過ぎてたらアウト
		if(diffMinute >= minutes) {
			return WebConsts.TIME_RUNNING_OUT;
		}
		
		// 日付、時間共に過ぎてないのでOK
		return WebConsts.TIME_WITHIN;
	}
	
	/**
	 * 時間、日付のdiffチェック(時間単位)
	 * @param targetTime     チェック日付対象
	 * @param minutes        分単位
	 * @return  true 日付過ぎてる false 日付過ぎてない
	 */
	public static final boolean checkDiffHour(LocalDateTime targetTime, int hours) {
		LocalDateTime now       = LocalDateTime.now();
		
		Long diffYears    =  ChronoUnit.YEARS.between(targetTime, now);
		Long diffMonth    =  ChronoUnit.MONTHS.between(targetTime, now);
		Long diffDay      =  ChronoUnit.DAYS.between(targetTime, now);
		Long diffHour     =  ChronoUnit.HOURS.between(targetTime, now);
		
		System.out.println(
				"OK [" + diffYears + "-" + diffMonth + "-" + diffDay + " " 
				+ diffHour + "]");
		
		// 日付、時間が過ぎてたらアウト
		if(diffYears > 0 || diffMonth > 0 || diffDay > 0) {
			return WebConsts.TIME_RUNNING_OUT;
		}
		
		// [hour]時間過ぎてたらアウト
		if(diffHour >= hours) {
			return WebConsts.TIME_RUNNING_OUT;
		}
		
		// 日付、時間共に過ぎてないのでOK
		return WebConsts.TIME_WITHIN;
	}
	
}
