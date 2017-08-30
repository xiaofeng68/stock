package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计表
 * @author Administrator
 *
 */
public class StockStatisticsModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private int year;
	private Date hhv_d;
	private double hhv_p;
	private Date llv_d;
	private double llv_p;
	private int days,v_days;
	private double hhv,llv;
	private Date marker_date,hhv_date,llv_date;
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
	public void setMarker_date(Date marker_date) {
		this.marker_date = marker_date;
	}
	public Date getMarker_date() {
		return marker_date;
	}
	public int getV_days() {
		return v_days;
	}
	public void setV_days(int v_days) {
		this.v_days = v_days;
	}
	public Date getHhv_date() {
		return hhv_date;
	}
	public void setHhv_date(Date hhv_date) {
		this.hhv_date = hhv_date;
	}
	public Date getLlv_date() {
		return llv_date;
	}
	public void setLlv_date(Date llv_date) {
		this.llv_date = llv_date;
	}
}
