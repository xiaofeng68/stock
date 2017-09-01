package com.kidinfor.quartz.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kidinfor.quartz.core.entity.Config;

/**
 * 定时任务
 * @author yu_qhai
 *
 */
public interface ConfigRepo extends JpaRepository<Config, Long> {
}
