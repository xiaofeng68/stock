package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.StockCode;

/**
 * 股票编码数据库访问层
 * @author yu_qhai
 *
 */
public interface StockCodeRepo extends JpaRepository<StockCode, Long> {
	@Modifying
    @Query(value = "truncate table t_stock_code",nativeQuery=true)
    void clear();
}
