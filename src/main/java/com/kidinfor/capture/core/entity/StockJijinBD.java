package com.kidinfor.capture.core.entity;

import javax.persistence.*;

import com.kidinfor.base.core.entity.BaseEntity;

import java.util.Date;

/**
 * 基金持仓变动
 * @author yu_qhai
 *
 */
@Entity
@Table(name = "t_stock_jijinbd")
public class StockJijinBD extends BaseEntity {
	@Column(columnDefinition="char(8) COMMENT '代码'")
    private String code;
    /**
     * 持股数量
     */
    @Column(columnDefinition="double COMMENT '持股数量'")
    private Double shareHDNum;
    /**
     * 持股增减
     */
    @Column(columnDefinition="double COMMENT '增减幅度'")
    private Double cgzj;
    /**
     * 增减幅度
     */
    @Column(columnDefinition="double COMMENT '增减幅度'")
    private Double cgzjd;
    /**
     * 持股市值
     */
    @Column(columnDefinition="double COMMENT '持股市值'")
    private Double vPosition;
    /**
     * 占总股本比例
     */
    @Column(columnDefinition="double COMMENT '占总股本比例'")
    private Double tabRate;
    /**
     * 占流通股比例
     */
    @Column(columnDefinition="double COMMENT '占流通股比例'")
    private Double lTZB;
    
    @Column(columnDefinition="date COMMENT '数据日期'")
    private String time;
    /**
     * 更新时间
     */
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public StockJijinBD() {
        this.updateAt = new Date();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public Double getShareHDNum() {
		return shareHDNum;
	}

	public void setShareHDNum(Double shareHDNum) {
		this.shareHDNum = shareHDNum;
	}

	public Double getCgzj() {
		return cgzj;
	}

	public void setCgzj(Double cgzj) {
		this.cgzj = cgzj;
	}

	public Double getCgzjd() {
		return cgzjd;
	}

	public void setCgzjd(Double cgzjd) {
		this.cgzjd = cgzjd;
	}

	public Double getvPosition() {
		return vPosition;
	}

	public void setvPosition(Double vPosition) {
		this.vPosition = vPosition;
	}

	public Double getlTZB() {
		return lTZB;
	}

	public void setlTZB(Double lTZB) {
		this.lTZB = lTZB;
	}

	public Double getTabRate() {
		return tabRate;
	}

	public void setTabRate(Double tabRate) {
		this.tabRate = tabRate;
	}

	public Double getLTZB() {
		return lTZB;
	}

	public void setLTZB(Double lTZB) {
		this.lTZB = lTZB;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
