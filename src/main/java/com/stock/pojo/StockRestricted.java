package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 个股解禁记录
 * @author yu_qhai
 *
 */
public class StockRestricted implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code,type;//,上市股份类型
	private Date day_d;//解禁日期
	private double allcount,tov,toa;//上市股份数量,占以流通数量比例,占总股本比例
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDay_d() {
		return day_d;
	}
	public void setDay_d(Date day_d) {
		this.day_d = day_d;
	}
	public double getAllcount() {
		return allcount;
	}
	public void setAllcount(double allcount) {
		this.allcount = allcount;
	}
	public double getTov() {
		return tov;
	}
	public void setTov(double tov) {
		this.tov = tov;
	}
	public double getToa() {
		return toa;
	}
	public void setToa(double toa) {
		this.toa = toa;
	}
	
}
