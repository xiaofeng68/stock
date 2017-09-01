package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kidinfor.capture.core.entity.StockCode;

/**
 * 股票编码数据库访问层
 * @author yu_qhai
 *
 */
public interface StockCodeRepo extends JpaRepository<StockCode, Long> {
}
