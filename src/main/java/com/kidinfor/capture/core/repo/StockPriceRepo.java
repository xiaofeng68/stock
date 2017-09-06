package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.StockPrice;

/**
 * 股票价格数据库访问层
 * @author yu_qhai
 *
 */
public interface StockPriceRepo extends JpaRepository<StockPrice, Long> {
	@Modifying
    @Query(value = "truncate table t_stock_price",nativeQuery=true)
    void clear();
}
