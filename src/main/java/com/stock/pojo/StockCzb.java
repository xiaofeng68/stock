package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * 个股财政部
 * @author yu_qhai
 *
 */
public class StockCzb implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String code,type_code;
	private Date day_d;
	private double buy_p,sell_p;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDay_d() {
		return day_d;
	}
	public void setDay_d(Date day_d) {
		this.day_d = day_d;
	}
	public double getBuy_p() {
		return buy_p;
	}
	public void setBuy_p(double buy_p) {
		this.buy_p = buy_p;
	}
	public double getSell_p() {
		return sell_p;
	}
	public void setSell_p(double sell_p) {
		this.sell_p = sell_p;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
}
