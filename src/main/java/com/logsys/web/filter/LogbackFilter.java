package com.logsys.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;

import com.authority.pojo.BaseUsers;
import com.authority.web.interseptor.WebConstants;

/**
 * 日志系统过滤器添加用户追踪
 * @author yu_qhai
 *
 */
public class LogbackFilter implements Filter {

	private final static String DEFAULT_USERID = "guest";
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		if (session == null) {
			MDC.put("userId", DEFAULT_USERID);
		} else {
			BaseUsers user = (BaseUsers) session.getAttribute(WebConstants.CURRENT_USER);
			if(user!=null){
				MDC.put("userId", user.getAccount());
				MDC.put("userName", user.getRealName());
			}else{
				MDC.put("userId", DEFAULT_USERID);
			}
		}
		chain.doFilter(request, response);
	}
	@Override
	public void init(FilterConfig Config) throws ServletException {
		
	}
}