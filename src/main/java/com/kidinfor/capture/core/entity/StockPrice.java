package com.kidinfor.capture.core.entity;

import javax.persistence.*;

import com.kidinfor.base.core.entity.BaseEntity;

import java.util.Date;

/**
 * 股票编号
 * @author yu_qhai
 *
 */
@Entity
@Table(name = "t_stock_price")
public class StockPrice extends BaseEntity {
	@Column(columnDefinition="char(8) COMMENT '代码'")
    private String code;
    /**
     * 当前价格
     */
    @Column(columnDefinition="double COMMENT '当前价格'")
    private Double price;
    /**
     * 成交量
     */
    @Column(columnDefinition="bigint COMMENT '成交量'")
    private Long cjl;
    /**
     * 成交额
     */
    @Column(columnDefinition="double COMMENT '成交额'")
    private Double cje;
    /**
     * 时间
     */
    @Column(columnDefinition="varchar(20) COMMENT '成交额'")
    private String time;
    /**
     * 今开
     */
    @Column(columnDefinition="double COMMENT '今开'")
    private Double jk;
    /**
     * 昨收
     */
    @Column(columnDefinition="double COMMENT '昨收'")
    private Double zs;
    /**
     * 最高
     */
    @Column(columnDefinition="double COMMENT '最高'")
    private Double zg;
    /**
     * 最低
     */
    @Column(columnDefinition="double COMMENT '最低'")
    private Double zd;
    
    /**
     * 更新时间
     */
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public StockPrice() {
        this.updateAt = new Date();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getCjl() {
		return cjl;
	}

	public void setCjl(Long cjl) {
		this.cjl = cjl;
	}

	public Double getCje() {
		return cje;
	}

	public void setCje(Double cje) {
		this.cje = cje;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getJk() {
		return jk;
	}

	public void setJk(Double jk) {
		this.jk = jk;
	}

	public Double getZs() {
		return zs;
	}

	public void setZs(Double zs) {
		this.zs = zs;
	}

	public Double getZg() {
		return zg;
	}

	public void setZg(Double zg) {
		this.zg = zg;
	}

	public Double getZd() {
		return zd;
	}

	public void setZd(Double zd) {
		this.zd = zd;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}


}
