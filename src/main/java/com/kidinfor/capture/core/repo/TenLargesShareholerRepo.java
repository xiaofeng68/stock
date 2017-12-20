package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kidinfor.capture.core.entity.TenLargestShareholder;

/**
 * 是大股东数据访问层
 * @author yu_qhai
 *
 */
public interface TenLargesShareholerRepo extends JpaRepository<TenLargestShareholder, Long> {
}
