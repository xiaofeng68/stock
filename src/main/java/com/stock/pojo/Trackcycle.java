package com.stock.pojo;

import java.io.Serializable;

public class Trackcycle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code, name, bus, track, form_des, stock_summary;
	private Double dwn_track_t, dwn_track_d, cen_track_t, cen_track_d,
			top_track_t, top_track_d, slf_track_t, slf_track_d, weekprice,
			monthprice, deepprice, targetprice;
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
	public String getBus() {
		return bus;
	}
	public void setBus(String bus) {
		this.bus = bus;
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
	public Double getDwn_track_t() {
		return dwn_track_t;
	}
	public void setDwn_track_t(Double dwn_track_t) {
		this.dwn_track_t = dwn_track_t;
	}
	public Double getDwn_track_d() {
		return dwn_track_d;
	}
	public void setDwn_track_d(Double dwn_track_d) {
		this.dwn_track_d = dwn_track_d;
	}
	public Double getCen_track_t() {
		return cen_track_t;
	}
	public void setCen_track_t(Double cen_track_t) {
		this.cen_track_t = cen_track_t;
	}
	public Double getCen_track_d() {
		return cen_track_d;
	}
	public void setCen_track_d(Double cen_track_d) {
		this.cen_track_d = cen_track_d;
	}
	public Double getTop_track_t() {
		return top_track_t;
	}
	public void setTop_track_t(Double top_track_t) {
		this.top_track_t = top_track_t;
	}
	public Double getTop_track_d() {
		return top_track_d;
	}
	public void setTop_track_d(Double top_track_d) {
		this.top_track_d = top_track_d;
	}
	public Double getSlf_track_t() {
		return slf_track_t;
	}
	public void setSlf_track_t(Double slf_track_t) {
		this.slf_track_t = slf_track_t;
	}
	public Double getSlf_track_d() {
		return slf_track_d;
	}
	public void setSlf_track_d(Double slf_track_d) {
		this.slf_track_d = slf_track_d;
	}
	public Double getWeekprice() {
		return weekprice;
	}
	public void setWeekprice(Double weekprice) {
		this.weekprice = weekprice;
	}
	public Double getMonthprice() {
		return monthprice;
	}
	public void setMonthprice(Double monthprice) {
		this.monthprice = monthprice;
	}
	public Double getDeepprice() {
		return deepprice;
	}
	public void setDeepprice(Double deepprice) {
		this.deepprice = deepprice;
	}
	public Double getTargetprice() {
		return targetprice;
	}
	public void setTargetprice(Double targetprice) {
		this.targetprice = targetprice;
	}
	
}
