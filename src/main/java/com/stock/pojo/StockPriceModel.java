package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 股价对象
 * @author Administrator
 *
 */
public class StockPriceModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;//股票编号
	private Date date;//股票日期
	private double open,hhv,llv,close,buy,vol,yclose;//开盘价，当日最高价，最低价，收盘价，成交金额，成交量，昨日收盘价
	private int state ;//1涨停,-2跌停
	private String color;//阴阳 
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHhv() {
		return hhv;
	}
	public void setHhv(double hhv) {
		this.hhv = hhv;
	}
	public double getLlv() {
		return llv;
	}
	public void setLlv(double llv) {
		this.llv = llv;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getBuy() {
		return buy;
	}
	public void setBuy(double buy) {
		this.buy = buy;
	}
	public double getVol() {
		return vol;
	}
	public void setVol(double vol) {
		this.vol = vol;
	}
	public double getYclose() {
		return yclose;
	}
	public void setYclose(double yclose) {
		this.yclose = yclose;
	}
	public String toString(){
		return code+","+open+","+hhv+","+llv;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
