package com.stock.pojo;

import java.io.Serializable;

/**
 * 个股轨道
 * 
 * @author yu_qhai
 * 
 */
public class StockTrack implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code, track, form_des, stock_summary;
	private double top_track_t, top_track_d, cen_track_t, cen_track_d,
			dwn_track_t, dwn_track_d,slf_track_t,slf_track_d,weekprice,monthprice,deepprice,targetprice;
	public double getTargetprice() {
		return targetprice;
	}
	public void setTargetprice(double targetprice) {
		this.targetprice = targetprice;
	}
	public double getWeekprice() {
		return weekprice;
	}
	public void setWeekprice(double weekprice) {
		this.weekprice = weekprice;
	}
	public double getMonthprice() {
		return monthprice;
	}
	public void setMonthprice(double monthprice) {
		this.monthprice = monthprice;
	}
	public double getDeepprice() {
		return deepprice;
	}
	public void setDeepprice(double deepprice) {
		this.deepprice = deepprice;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getForm_des() {
		return form_des;
	}
	public void setForm_des(String form_des) {
		this.form_des = form_des;
	}
	public String getStock_summary() {
		return stock_summary;
	}
	public void setStock_summary(String stock_summary) {
		this.stock_summary = stock_summary;
	}
	public double getTop_track_t() {
		return top_track_t;
	}
	public void setTop_track_t(double top_track_t) {
		this.top_track_t = top_track_t;
	}
	public double getTop_track_d() {
		return top_track_d;
	}
	public void setTop_track_d(double top_track_d) {
		this.top_track_d = top_track_d;
	}
	public double getCen_track_t() {
		return cen_track_t;
	}
	public void setCen_track_t(double cen_track_t) {
		this.cen_track_t = cen_track_t;
	}
	public double getCen_track_d() {
		return cen_track_d;
	}
	public void setCen_track_d(double cen_track_d) {
		this.cen_track_d = cen_track_d;
	}
	public double getDwn_track_t() {
		return dwn_track_t;
	}
	public void setDwn_track_t(double dwn_track_t) {
		this.dwn_track_t = dwn_track_t;
	}
	public double getDwn_track_d() {
		return dwn_track_d;
	}
	public void setDwn_track_d(double dwn_track_d) {
		this.dwn_track_d = dwn_track_d;
	}
	public void setSlf_track_t(double slf_track_t) {
		this.slf_track_t = slf_track_t;
	}
	public double getSlf_track_t() {
		return slf_track_t;
	}
	public void setSlf_track_d(double slf_track_d) {
		this.slf_track_d = slf_track_d;
	}
	public double getSlf_track_d() {
		return slf_track_d;
	}
	
}
