package com.kidinfor.capture.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kidinfor.base.core.entity.BaseEntity;

@Entity
@Table(name = "t_stock_tenld_shareholder")
public class TenldLargestShareholder extends BaseEntity {
	
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 日期
	 */
	private String rq;
    /**
     * 
     */
    private Integer mc;
    
    /**
     * 股东名称
     */
    private String gdmc;
    /**
     * 股份类型
     */
    private String gflx;
    /**
     * 持股数
     */
    private Long cgs;
    /**
     * 占总股本比例
     */
    private Double zltgbcgbl;
    /**
     * 增减
     */
    private String zj;
    /**
     * 变动比例
     */
    private Double bdbl;
    
    /**
     * 更新时间
     */
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public TenldLargestShareholder() {
        this.updateAt = new Date();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public Integer getMc() {
		return mc;
	}

	public void setMc(Integer mc) {
		this.mc = mc;
	}

	public String getGdmc() {
		return gdmc;
	}

	public void setGdmc(String gdmc) {
		this.gdmc = gdmc;
	}

	public String getGflx() {
		return gflx;
	}

	public void setGflx(String gflx) {
		this.gflx = gflx;
	}

	public Long getCgs() {
		return cgs;
	}

	public void setCgs(Long cgs) {
		this.cgs = cgs;
	}

	public Double getZltgbcgbl() {
		return zltgbcgbl;
	}

	public void setZltgbcgbl(Double zltgbcgbl) {
		this.zltgbcgbl = zltgbcgbl;
	}

	public String getZj() {
		return zj;
	}

	public void setZj(String zj) {
		this.zj = zj;
	}

	public Double getBdbl() {
		return bdbl;
	}

	public void setBdbl(Double bdbl) {
		this.bdbl = bdbl;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
