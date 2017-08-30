package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

public class StockHandler implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code,stockholder,type,state,inout;
	private Date day_d;
	private double stockcount,stock_ratio;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStockholder() {
		return stockholder;
	}
	public void setStockholder(String stockholder) {
		this.stockholder = stockholder;
	}
	public double getStockcount() {
		return stockcount;
	}
	public void setStockcount(double stockcount) {
		this.stockcount = stockcount;
	}
	public double getStock_ratio() {
		return stock_ratio;
	}
	public void setStock_ratio(double stock_ratio) {
		this.stock_ratio = stock_ratio;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setDay_d(Date day_d) {
		this.day_d = day_d;
	}
	public Date getDay_d() {
		return day_d;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setInout(String inout) {
		this.inout = inout;
	}
	public String getInout() {
		return inout;
	}
	
}
