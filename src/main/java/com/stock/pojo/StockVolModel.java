package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 历史量能
 * @author yu_qhai
 *
 */
public class StockVolModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Date vol_date;
	private double vol;
	private String type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getVol_date() {
		return vol_date;
	}
	public void setVol_date(Date vol_date) {
		this.vol_date = vol_date;
	}
	public double getVol() {
		return vol;
	}
	public void setVol(double vol) {
		this.vol = vol;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
