package com.kidinfor.capture.core.entity;

import javax.persistence.*;

import com.kidinfor.base.core.entity.BaseEntity;

import java.util.Date;

/**
 * 主题猎手-个股龙头
 * @author yu_qhai
 *
 */
@Entity
@Table(name = "t_stock_lt")
public class StockLt extends BaseEntity {
	@Column(columnDefinition="char(8) COMMENT '板块'")
    private String bk;
	@Column(columnDefinition="char(8) COMMENT '编号'")
    private String code;
    /**
     * 名称
     */
    @Column(columnDefinition="varchar(20) COMMENT '名称'")
    private String name;
    /**
     * 最新价
     */
    @Column(columnDefinition="double COMMENT '最新价'")
    private Double price;
    @Column(columnDefinition="double COMMENT '昨收'")
    private Double lastDay;
    @Column(columnDefinition="double COMMENT '今开'")
    private Double open;
    @Column(columnDefinition="double COMMENT '最高价'")
    private Double heigh;
    @Column(columnDefinition="double COMMENT '最低价'")
    private Double low;
    /**
     * 涨跌额
     */
    @Column(columnDefinition="double COMMENT '涨跌额'")
    private Double ud;
    /**
     * 涨跌幅
     */
    @Column(columnDefinition="varchar(10) COMMENT '涨跌幅'")
    private String udrate;
    /**
     * 成交额
     */
    @Column(columnDefinition="double COMMENT '成交额'")
    private Double cge;
    /**
     * 成交量
     */
    @Column(columnDefinition="int COMMENT '成交量'")
    private Integer cgl;
    
    
    
    /**
     * 排序
     */
    @Column(columnDefinition="int COMMENT '排序'")
    private Integer seq;
    
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updateAt;

    public StockLt() {
        this.updateAt = new Date();
    }

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


	public Double getPrice() {
		return price;
	}

	public Double getUd() {
		return ud;
	}

	public void setUd(Double ud) {
		this.ud = ud;
	}

	public String getUdrate() {
		return udrate;
	}

	public void setUdrate(String udrate) {
		this.udrate = udrate;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHeigh() {
		return heigh;
	}

	public void setHeigh(Double heigh) {
		this.heigh = heigh;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getCge() {
		return cge;
	}

	public void setCge(Double cge) {
		this.cge = cge;
	}

	public Integer getCgl() {
		return cgl;
	}

	public void setCgl(Integer cgl) {
		this.cgl = cgl;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getLastDay() {
		return lastDay;
	}

	public void setLastDay(Double lastDay) {
		this.lastDay = lastDay;
	}

	public String getBk() {
		return bk;
	}

	public void setBk(String bk) {
		this.bk = bk;
	}

}
