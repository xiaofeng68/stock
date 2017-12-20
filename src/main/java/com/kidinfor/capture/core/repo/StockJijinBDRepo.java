package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kidinfor.capture.core.entity.StockJijinBD;

/**
 * 基金持仓变动
 * @author yu_qhai
 *
 */
public interface StockJijinBDRepo extends JpaRepository<StockJijinBD, Long> {
}
