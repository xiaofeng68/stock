package com.stock.pojo;

import java.io.Serializable;

/**
 * 年个股收益
 * @author yu_qhai
 *
 */
public class StockIncoming implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private int year;
	private double incoming,allincoming,sumincoming,capital,nprice;//每股收益,营业收入，利润总额,流通股本，最新价
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getIncoming() {
		return incoming;
	}
	public void setIncoming(double incoming) {
		this.incoming = incoming;
	}
	public double getAllincoming() {
		return allincoming;
	}
	public void setAllincoming(double allincoming) {
		this.allincoming = allincoming;
	}
	public double getSumincoming() {
		return sumincoming;
	}
	public void setSumincoming(double sumincoming) {
		this.sumincoming = sumincoming;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public double getNprice() {
		return nprice;
	}
	public void setNprice(double nprice) {
		this.nprice = nprice;
	}
	
}
