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
	@Column(columnDefinition="varchar(100) COMMENT '基金名称'")
	private String sHName;
	@Column(columnDefinition="varchar(10) COMMENT '基金编号'")
	private String sHCode;
	/**
	 * 基金类型
	 */
	@Column(columnDefinition="varchar(10) COMMENT '基金类型'")
	private String type;
    /**
     * 持股数量
     */
    @Column(columnDefinition="double COMMENT '持股数量'")
    private Double shareHDNum;
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
     * 购买类型
     */
    @Column(columnDefinition="varchar(10) COMMENT '占流通股比例'")
    private String buyState;
    
    @Column(columnDefinition="date COMMENT '数据日期'")
    private Date time;
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

	public Double getVPosition() {
		return vPosition;
	}

	public void setVPosition(Double vPosition) {
		this.vPosition = vPosition;
	}

	public Double getTabRate() {
		return tabRate;
	}

	public void setTabRate(Double tabRate) {
		this.tabRate = tabRate;
	}

	public String getSHName() {
		return sHName;
	}

	public void setSHName(String sHName) {
		this.sHName = sHName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyState() {
		return buyState;
	}

	public void setBuyState(String buyState) {
		this.buyState = buyState;
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

	public String getSHCode() {
		return sHCode;
	}

	public void setSHCode(String sHCode) {
		this.sHCode = sHCode;
	}
}
