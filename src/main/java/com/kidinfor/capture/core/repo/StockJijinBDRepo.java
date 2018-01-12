package com.kidinfor.capture.core.repo;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.StockJijinBD;

/**
 * 基金持仓变动
 * @author yu_qhai
 *
 */
public interface StockJijinBDRepo extends JpaRepository<StockJijinBD, Long> {
	@Query("select max(time) from StockJijinBD where code =?")
    Date findMaxDate(String code);
}
