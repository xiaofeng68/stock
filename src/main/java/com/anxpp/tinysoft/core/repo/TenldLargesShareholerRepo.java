package com.anxpp.tinysoft.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.anxpp.tinysoft.core.entity.TenldLargestShareholder;

/**
 * 十大流通股东数据访问层
 * @author yu_qhai
 *
 */
public interface TenldLargesShareholerRepo extends JpaRepository<TenldLargestShareholder, Long> {
}
