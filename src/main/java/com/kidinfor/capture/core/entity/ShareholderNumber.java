package com.kidinfor.capture.core.entity;

import javax.persistence.*;

import com.kidinfor.base.core.entity.BaseEntity;

import java.util.Date;

/**
 * gdrs
 */
@Entity
@Table(name = "t_stock_shareholder_number")
public class ShareholderNumber extends BaseEntity {
    /**
     * 代码
     */
	@Column(columnDefinition="char(8) COMMENT '代码'")
    private String code;
	/**
	 * 日期
	 */
	@Column(columnDefinition="char(10) COMMENT '日期'")
	private String rq;
    /**
     * 股东人数
     */
	@Column(columnDefinition="double COMMENT '股东人数'")
    private Double gdrs;
    /**
     * 较上期变化
     */
	@Column(columnDefinition="double COMMENT '较上期变化'")
    private Double gdrs_jsqbh;
    /**
     * 人均流通股
     */
	@Column(columnDefinition="double COMMENT '人均流通股'")
    private Double rjltg;
    /**
     * 较上期变化
     */
	@Column(columnDefinition="double COMMENT '较上期变化'")
    private Double rjltg_jsqbh;
    /**
     * 筹码集中度
     */
	@Column(columnDefinition="varchar(20) COMMENT '筹码集中度'")
    private String cmjzd;
    /**
     * 股价
     */
	@Column(columnDefinition="double COMMENT '股价'")
    private Double gj;
    /**
     * 人均持股金额
     */
	@Column(columnDefinition="double COMMENT '人均持股金额'")
    private Double rjcgje;
    /**
     * 前十大股东持股合计
     */
	@Column(columnDefinition="double COMMENT '前十大股东持股合计'")
    private Double qsdgdcghj;
    /**
     * 前十大流通股东持股合计
     */
	@Column(columnDefinition="double COMMENT '前十大流通股东持股合计'")
    private Double qsdltgdcghj;
    /**
     * 更新时间
     */
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public ShareholderNumber() {
        this.updateAt = new Date();
    }

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public Double getGdrs() {
		return gdrs;
	}

	public void setGdrs(Double gdrs) {
		this.gdrs = gdrs;
	}

	public Double getGdrs_jsqbh() {
		return gdrs_jsqbh;
	}

	public void setGdrs_jsqbh(Double gdrs_jsqbh) {
		this.gdrs_jsqbh = gdrs_jsqbh;
	}

	public Double getRjltg() {
		return rjltg;
	}

	public void setRjltg(Double rjltg) {
		this.rjltg = rjltg;
	}

	public Double getRjltg_jsqbh() {
		return rjltg_jsqbh;
	}

	public void setRjltg_jsqbh(Double rjltg_jsqbh) {
		this.rjltg_jsqbh = rjltg_jsqbh;
	}

	public String getCmjzd() {
		return cmjzd;
	}

	public void setCmjzd(String cmjzd) {
		this.cmjzd = cmjzd;
	}

	public Double getGj() {
		return gj;
	}

	public void setGj(Double gj) {
		this.gj = gj;
	}

	public Double getRjcgje() {
		return rjcgje;
	}

	public void setRjcgje(Double rjcgje) {
		this.rjcgje = rjcgje;
	}

	public Double getQsdgdcghj() {
		return qsdgdcghj;
	}

	public void setQsdgdcghj(Double qsdgdcghj) {
		this.qsdgdcghj = qsdgdcghj;
	}

	public Double getQsdltgdcghj() {
		return qsdltgdcghj;
	}

	public void setQsdltgdcghj(Double qsdltgdcghj) {
		this.qsdltgdcghj = qsdltgdcghj;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
