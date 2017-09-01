package com.anxpp.tinysoft.core.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 股票编号
 * @author yu_qhai
 *
 */
@Entity
@Table(name = "t_stock_code")
public class StockCode extends BaseEntity {
    private String code;
    private String name;
    private String scode;

    /**
     * 更新时间
     */
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public StockCode() {
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

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

    
}
