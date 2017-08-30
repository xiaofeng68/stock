package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 量能预警实体
 * @author yu_qhai
 *
 */
public class StockWarn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code,name;
	private Date date;
	private double yclose,sumprice,nprice;//最新价,当日成交金额
	private long capital;//流通股
	private double tov;//比值
	private int state;//标志比值是涨还是跌：0未知，1涨2跌
	private double zhang,minPrice;//涨幅，最低价
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getYclose() {
		return yclose;
	}
	public void setYclose(double yclose) {
		this.yclose = yclose;
	}
	public double getNprice() {
		return nprice;
	}
	public void setNprice(double nprice) {
		this.nprice = nprice;
	}
	public double getTov() {
		return tov;
	}
	public void setTov(double tov) {
		this.tov = tov;
	}
	public double getSumprice() {
		return sumprice;
	}
	public void setSumprice(double sumprice) {
		this.sumprice = sumprice;
	}
	public double getZhang() {
		return zhang;
	}
	public void setZhang(double zhang) {
		this.zhang = zhang;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getCapital() {
		return capital;
	}
	public void setCapital(long capital) {
		this.capital = capital;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
