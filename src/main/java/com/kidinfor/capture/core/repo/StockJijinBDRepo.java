package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.StockJijinBD;

/**
 * 基金持仓变动
 * @author yu_qhai
 *
 */
public interface StockJijinBDRepo extends JpaRepository<StockJijinBD, Long> {
	@Modifying
    @Query(value = "truncate table t_stock_jijinbd",nativeQuery=true)
    void clear();
}
