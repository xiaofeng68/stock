package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kidinfor.capture.core.entity.ShareholderNumber;

/**
 * 股东人数数据访问层
 * @author yu_qhai
 *
 */
public interface ShareholderNumberRepo extends JpaRepository<ShareholderNumber, Long> {
}
