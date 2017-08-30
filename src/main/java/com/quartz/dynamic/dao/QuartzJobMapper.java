package com.quartz.dynamic.dao;

import java.util.List;

import com.authority.pojo.Criteria;
import com.quartz.dynamic.pojo.QuartzJob;

/**
 * 动态任务Bean
 * @author yu_qhai
 *
 */
public interface QuartzJobMapper {
	/**
	 * 根据条件查询记录集
	 */
	List<QuartzJob> selectByExample(Criteria example);
	/**
	 * 根据条件查询记录总数
	 */
	int countByExample(Criteria example);
	/**
	 * 根据主键删除记录
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 保存属性不为空的记录
	 */
	int insert(QuartzJob record);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(QuartzJob record);
}