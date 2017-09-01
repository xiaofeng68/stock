package com.anxpp.tinysoft.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.anxpp.tinysoft.core.entity.StockCode;

/**
 * 股票编码数据库访问层
 * @author yu_qhai
 *
 */
public interface StockCodeRepo extends JpaRepository<StockCode, Long> {
}
