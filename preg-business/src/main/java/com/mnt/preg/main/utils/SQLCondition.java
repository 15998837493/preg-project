/**
 * 
 */
package com.mnt.preg.main.utils;

/**
 * 
 * 条件参数
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-10-30 zy 初版
 */
public class SQLCondition {

	// 条件名称
	public String name;
	// 条件符合
	public String symbol;
	// 条件值
	public Object value;

	public SQLCondition(String name, String symbol, Object value) {
		super();
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
