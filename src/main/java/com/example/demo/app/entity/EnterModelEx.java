package com.example.demo.app.entity;

import com.example.demo.common.number.RoomMaxNumber;

/**
 * 入室モデル拡張版
 * @author nanai
 *
 */
public class EnterModelEx extends EnterModel {
	
	private RoomMaxNumber max_sum;		/** 入室数  */
	
	/**
	 * コンストラクタ
	 * @param model
	 */
	public EnterModelEx(
			EnterModel model) {
		super(model);
		
		this.max_sum = new RoomMaxNumber(0);
	}
	
	/**
	 * コンストラクタ
	 * @param model
	 * @param max_sum
	 */
	public EnterModelEx(
			EnterModel model,
			RoomMaxNumber max_sum) {
		super(model);
		
		this.max_sum = (max_sum == null ?
			new RoomMaxNumber(0) :
			new RoomMaxNumber(max_sum.getNumber()));
	}
	
	/**
	 * コンストラクタ
	 * @param model
	 */
	public EnterModelEx(
			EnterModelEx model) {
		super(model);
		
		this.max_sum = (model == null ?
				new RoomMaxNumber(0) :
				new RoomMaxNumber(model.getMax_sum()));
	}
	
	public int getMax_sum() {
		return max_sum.getNumber();
	}

	protected void setMax_sum(int max_sum) {
		this.max_sum = new RoomMaxNumber(max_sum);
	}
}
