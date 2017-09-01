package com.anxpp.tinysoft.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.anxpp.tinysoft.core.entity.TenLargestShareholder;

/**
 * 是大股东数据访问层
 * @author yu_qhai
 *
 */
public interface TenLargesShareholerRepo extends JpaRepository<TenLargestShareholder, Long> {
}
