package com.kidinfor.capture.core.entity;

import javax.persistence.*;

import com.kidinfor.base.core.entity.BaseEntity;

import java.util.Date;

/**
 * 基金持仓
 * @author yu_qhai
 *
 */
@Entity
@Table(name = "t_stock_jijincc")
public class StockJijinCC extends BaseEntity {
	@Column(columnDefinition="char(8) COMMENT '代码'")
    private String code;
    /**
     * 基金家数
     */
    @Column(columnDefinition="int COMMENT '基金家数'")
    private Integer num;
    /**
     * 持股变化
     */
    @Column(columnDefinition="varchar(6) COMMENT '持股变化'")
    private String cGChange;
    /**
     * 持股总数
     */
    @Column(columnDefinition="int COMMENT '持股总数'")
    private Integer shareHDNum;
    /**
     * 持股市值
     */
    @Column(columnDefinition="double COMMENT '持股市值'")
    private Double vPosition;
    /**
     * 持股变动数值
     */
    @Column(columnDefinition="double COMMENT '持股变动数值'")
    private Double shareHDNumChange;
    /**
     * 持股变动比例
     */
    @Column(columnDefinition="double COMMENT '持股变动比例'")
    private Double rateChange;
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
    private Date time;
    /**
     * 更新时间
     */
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public StockJijinCC() {
        this.updateAt = new Date();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getcGChange() {
		return cGChange;
	}

	public void setcGChange(String cGChange) {
		this.cGChange = cGChange;
	}

	public Integer getShareHDNum() {
		return shareHDNum;
	}

	public void setShareHDNum(Integer shareHDNum) {
		this.shareHDNum = shareHDNum;
	}

	public Double getVPosition() {
		return vPosition;
	}

	public void setVPosition(Double vPosition) {
		this.vPosition = vPosition;
	}

	public Double getShareHDNumChange() {
		return shareHDNumChange;
	}

	public void setShareHDNumChange(Double shareHDNumChange) {
		this.shareHDNumChange = shareHDNumChange;
	}

	public Double getRateChange() {
		return rateChange;
	}

	public void setRateChange(Double rateChange) {
		this.rateChange = rateChange;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
