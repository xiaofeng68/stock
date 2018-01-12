package com.kidinfor.capture.core.entity;

import javax.persistence.*;

import com.kidinfor.base.core.entity.BaseEntity;

import java.util.Date;

/**
 * 主题猎手
 * @author yu_qhai
 *
 */
@Entity
@Table(name = "t_stock_bk")
public class StockBk extends BaseEntity {
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
    private Long price;
    /**
     * 涨跌额
     */
    @Column(columnDefinition="double COMMENT '涨跌额'")
    private Double ud;
    /**
     * 涨跌幅
     */
    @Column(columnDefinition="double COMMENT '涨跌幅'")
    private String udrate;
    /**
     * 总市值
     */
    @Column(columnDefinition="double COMMENT '总市值'")
    private Double market;
    /**
     * 换手率
     */
    @Column(columnDefinition="double COMMENT '换手率'")
    private Double rate;
    /**
     * 上涨家数
     */
    @Column(columnDefinition="int COMMENT '上涨家数'")
    private Integer upnum;
    /**
     * 下跌家数
     */
    @Column(columnDefinition="int COMMENT '下跌家数'")
    private Integer downnum;
    
    /**
     * 排序
     */
    @Column(columnDefinition="int COMMENT '排序'")
    private Integer seq;
    
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updateAt;

    public StockBk() {
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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
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

	public Double getMarket() {
		return market;
	}

	public void setMarket(Double market) {
		this.market = market;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getUpnum() {
		return upnum;
	}

	public void setUpnum(Integer upnum) {
		this.upnum = upnum;
	}

	public Integer getDownnum() {
		return downnum;
	}

	public void setDownnum(Integer downnum) {
		this.downnum = downnum;
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

}
