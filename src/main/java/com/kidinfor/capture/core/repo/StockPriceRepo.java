package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kidinfor.capture.core.entity.StockPrice;

/**
 * 股票价格数据库访问层
 * @author yu_qhai
 *
 */
public interface StockPriceRepo extends JpaRepository<StockPrice, Long> {
}
