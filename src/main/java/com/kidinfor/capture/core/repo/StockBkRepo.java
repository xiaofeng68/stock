package com.kidinfor.capture.core.repo;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.kidinfor.capture.core.entity.StockBk;

/**
 * 主题猎手，板块变动
 * @author yu_qhai
 *
 */
public interface StockBkRepo extends JpaRepository<StockBk, Long> {
	@Query("select code from StockBk o where o.updateAt = ?1  order by seq")
	List<String> findCodes(Date date);
	@Modifying
	@Transactional
	@Query("delete from StockBk o where o.updateAt = ?1 ")
	void deleteByUpdateAt(Date date);
}
