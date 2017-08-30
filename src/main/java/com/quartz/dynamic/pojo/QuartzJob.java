package com.quartz.dynamic.pojo;

import java.io.Serializable;

/**
 * 定时任务
 * 同步的执行类，需要从StatefulMethodInvokingJob继承
 * 异步的执行类，需要从MethodInvokingJob继承
 * @author yu_qhai
 *
 */
public class QuartzJob implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String code; // 任务的Id，一般为所定义Bean的ID
	private String des; // 任务的描述
	private String gcode; // 任务所属组的编号、名称
	private int state; // 任务的状态，0：启用；1：禁用；2：已删除
	private String exp; // 定时任务运行时间表达式
	private String clsdes;//定时任务执行类 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return this.code+"Trigger";
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getGcode() {
		return gcode;
	}
	public void setGcode(String gcode) {
		this.gcode = gcode;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getClsdes() {
		return clsdes;
	}
	public void setClsdes(String clsdes) {
		this.clsdes = clsdes;
	}
}
