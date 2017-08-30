package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 月统计表
 * @author Administrator
 *
 */
public class StockStatisticsMonthModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private int year,month;
	private Date hhv_d;
	private double hhv_p;
	private Date llv_d;
	private double llv_p;
	private int days;
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
	public Date getHhv_d() {
		return hhv_d;
	}
	public void setHhv_d(Date hhv_d) {
		this.hhv_d = hhv_d;
	}
	public double getHhv_p() {
		return hhv_p;
	}
	public void setHhv_p(double hhv_p) {
		this.hhv_p = hhv_p;
	}
	public Date getLlv_d() {
		return llv_d;
	}
	public void setLlv_d(Date llv_d) {
		this.llv_d = llv_d;
	}
	public double getLlv_p() {
		return llv_p;
	}
	public void setLlv_p(double llv_p) {
		this.llv_p = llv_p;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
}
