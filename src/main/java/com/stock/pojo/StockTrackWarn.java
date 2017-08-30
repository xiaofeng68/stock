package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 轨道预警
 * @author yu_qhai
 *
 */
public class StockTrackWarn implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code,track,track_ud;
	private Date start;
	private double zhang,nprice;
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
	public String getTrack_ud() {
		return track_ud;
	}
	public void setTrack_ud(String track_ud) {
		this.track_ud = track_ud;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public double getZhang() {
		return zhang;
	}
	public void setZhang(double zhang) {
		this.zhang = zhang;
	}
	public double getNprice() {
		return nprice;
	}
	public void setNprice(double nprice) {
		this.nprice = nprice;
	}
	
}
