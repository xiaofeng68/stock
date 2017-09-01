package com.anxpp.tinysoft.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.anxpp.tinysoft.core.entity.ShareholderNumber;

/**
 * 股东人数数据访问层
 * @author yu_qhai
 *
 */
public interface ShareholderNumberRepo extends JpaRepository<ShareholderNumber, Long> {
}
