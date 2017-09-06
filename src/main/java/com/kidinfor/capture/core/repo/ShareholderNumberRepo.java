package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kidinfor.capture.core.entity.ShareholderNumber;

/**
 * 股东人数数据访问层
 * @author yu_qhai
 *
 */
public interface ShareholderNumberRepo extends JpaRepository<ShareholderNumber, Long> {
	@Modifying
    @Query(value = "truncate table t_stock_shareholder_number",nativeQuery=true)
    void clear();
}
