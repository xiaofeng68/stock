package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.TenldLargestShareholder;

/**
 * 十大流通股东数据访问层
 * @author yu_qhai
 *
 */
public interface TenldLargesShareholerRepo extends JpaRepository<TenldLargestShareholder, Long> {
	@Modifying
    @Query(value = "truncate table t_stock_tenld_shareholder",nativeQuery=true)
    void clear();
}
