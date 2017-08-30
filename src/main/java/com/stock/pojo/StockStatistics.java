package com.stock.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票统计
 * @author yu_qhai
 *
 */
public class StockStatistics implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 股票编号
	 */
	private String code;
	/**
	 * 股票名称
	 */
	private String name;
	/**
	 * 所属行业 
	 */
	private String bus;
	/**
	 * 上市日期
	 */
	private Date marketDate;
	/**
	 * 最高点位
	 */
	private double hhv;
	/**
	 * 最高点位日期
	 */
	private Date hhvDate;
	/**
	 * 最低点位
	 */
	private double llv;
	/**
	 * 最低点位日期
	 */
	private Date llvDate;
	/**
	 * 交易日
	 */
	private int vDays;
	/**
	 * 量能预警
	 */
	private int num;
	/**
	 * 自定义板块
	 */
	private String statestr;
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
	public Date getMarketDate() {
		return marketDate;
	}
	public void setMarketDate(Date marketDate) {
		this.marketDate = marketDate;
	}
	public double getHhv() {
		return hhv;
	}
	public void setHhv(double hhv) {
		this.hhv = hhv;
	}
	public Date getHhvDate() {
		return hhvDate;
	}
	public void setHhvDate(Date hhvDate) {
		this.hhvDate = hhvDate;
	}
	public double getLlv() {
		return llv;
	}
	public void setLlv(double llv) {
		this.llv = llv;
	}
	public Date getLlvDate() {
		return llvDate;
	}
	public void setLlvDate(Date llvDate) {
		this.llvDate = llvDate;
	}
	public int getvDays() {
		return vDays;
	}
	public void setvDays(int vDays) {
		this.vDays = vDays;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getStatestr() {
		return statestr;
	}
	public void setStatestr(String statestr) {
		this.statestr = statestr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
