package com.quartz.dynamic.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.pojo.Criteria;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtPager;
import com.authority.pojo.ExtReturn;
import com.quartz.dynamic.pojo.QuartzJob;
import com.quartz.dynamic.service.QuartzManagerService;

/**
 * 动态任务管理
 * @author yu_qhai
 *
 */
@Controller
@RequestMapping("/quartz")
public class QuartzManagerController {
	private Logger logger = LoggerFactory.getLogger(QuartzManagerController.class);
	@Autowired
	private QuartzManagerService quartzManagerService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * index
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "quartz/dynamic";
	}

	/**
	 * 所有系统字段
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Object all(ExtPager pager, @RequestParam(required = false) String name) {
		try {
			Criteria criteria = new Criteria();
			// 设置分页信息
			if (pager.getLimit() != null && pager.getStart() != null) {
				criteria.setMysqlLength(pager.getLimit());
				criteria.setMysqlOffset(pager.getStart());
			}
			// 排序信息
			if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
				criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
			} else {
				criteria.setOrderByClause(" job_id desc ");
			}
			if (StringUtils.isNotBlank(name)) {
				criteria.put("name", name);
			}
			List<QuartzJob> list = this.quartzManagerService.selectByExample(criteria);
			int total = this.quartzManagerService.countByExample(criteria);
			logger.debug("total:{}", total);
			return new ExtGridReturn(total, list);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Object save(QuartzJob job) {
		try {
			Criteria criteria = new Criteria();
			criteria.put("quartz", job);
			String result = this.quartzManagerService.saveFields(criteria);
			if ("01".equals(result)) {
				return new ExtReturn(true, "保存成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "保存失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/del/{id}")
	@ResponseBody
	public Object delete(@PathVariable String id) {
		try {
			Criteria criteria = new Criteria();
			criteria.put("id", id);
			String result = this.quartzManagerService.delete(criteria);
			if ("01".equals(result)) {
				return new ExtReturn(true, "删除成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "删除失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	/**启动动态任务*/
	@RequestMapping("/start")
	@ResponseBody
	public Object start(QuartzJob job) {
		try {
			boolean started = this.quartzManagerService.enableCronSchedule(job,null);
			if (started) {
				//同步任务状态到数据库
				job.setState(0);
				this.quartzManagerService.updateQuartzJob(job);
				return new ExtReturn(true, "启动成功！");
			}else{
				return new ExtReturn(false, "启动失败！");
			} 
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	/**停止动态任务*/
	@RequestMapping("/stop")
	@ResponseBody
	public Object stop(QuartzJob job) {
		try {
			boolean started = this.quartzManagerService.disableSchedule(job.getCode(),job.getGcode());
			if (started) {
				job.setState(1);
				this.quartzManagerService.updateQuartzJob(job);
				return new ExtReturn(true, "停止成功！");
			}else{
				return new ExtReturn(false, "停止失败！");
			} 
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
}
