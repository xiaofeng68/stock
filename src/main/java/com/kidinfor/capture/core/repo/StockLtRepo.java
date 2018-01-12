package com.kidinfor.capture.core.repo;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.kidinfor.capture.core.entity.StockLt;

/**
 * 主题猎手，个股龙头
 * @author yu_qhai
 *
 */
public interface StockLtRepo extends JpaRepository<StockLt, Long> {
	/**
	 * 只保留当天最新的记录
	 * @param date
	 */
	@Modifying
	@Transactional
	@Query("delete from StockLt o where o.updateAt = ?1 ")
	void deleteByUpdateAt(Date date);
}
