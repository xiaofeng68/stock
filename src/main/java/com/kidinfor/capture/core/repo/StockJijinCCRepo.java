package com.kidinfor.capture.core.repo;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.StockJijinCC;

/**
 * 基金持仓
 * @author yu_qhai
 *
 */
public interface StockJijinCCRepo extends JpaRepository<StockJijinCC, Long> {
	
	
	@Query("select max(time) from StockJijinCC")
    Date findMaxDate();
	@Query("select code from StockJijinCC group by code")
	List<String> findJJCodes();
}
