package com.kidinfor.quartz.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kidinfor.base.core.entity.BaseEntity;
@Entity
@Table(name = "t_quartz_conf")
public class Config extends BaseEntity {
	
	/**
	 * 任务名称
	 */
	private String name;
	/**
	 * 任务执行表达式
	 */
	private String cron;
	/**
	 * 任务描述
	 */
	private String des;
	 /**
     * 更新时间
     */
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public Config() {
        this.updateAt = new Date();
    }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDes() {
		return des;
	}


	public void setDes(String des) {
		this.des = des;
	}


	public Date getUpdateAt() {
		return updateAt;
	}


	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}


	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

}