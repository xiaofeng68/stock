package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.TenLargestShareholder;

/**
 * 是大股东数据访问层
 * @author yu_qhai
 *
 */
public interface TenLargesShareholerRepo extends JpaRepository<TenLargestShareholder, Long> {
	@Modifying
    @Query(value = "truncate table t_stock_ten_shareholder",nativeQuery=true)
    void clear();
}
