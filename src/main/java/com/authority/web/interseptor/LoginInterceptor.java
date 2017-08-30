package com.authority.web.interseptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.authority.common.utils.StringUtil;
import com.kidinfor.lesence.RSAUtils;

/**
 * 验证用户登陆拦截器
 * 
 * @author Administrator
 * @date 2011-3-13 下午09:02:00
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Value("${public.key:MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUaig+svb77cRBp9zB8q1P3TdSGlOWaB4SCMJgbO6jF3AIbEgWkKBVwIdHy1UZnSeB1ojTEZreE9jNpl/YbO/SPSNiqYAvd/G+0m4IX/yWMYyzU5O+HZNSPOM7i6oOSiZ0Fyh1Rv68B3Wpa8Me97lccEf3VoIT6P3JYuZMLNPAsQIDAQAB}")
    private String publicKey;
    @Value("${lesence.code:288617512F4A83BA188AB46E9B1CCBC505B3A2FE71076FBFD5249501576FA9F573CE329C9BDEBB6C0F5084DE281A849A6158AD38FADC1ED3CD754D3FF8716EBBAB5B56B1E56F74F55A6FF576FB5BAA92B1128F616672C7049B0B7E67F99803DF8BFD0395EEF00B2A7D3598BEFA2ABBE38682D402F64056028BEEAD838BCBA254}")
    private String lesenceCode;
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    String target = RSAUtils.decryptByPublicKey(lesenceCode, publicKey);
        String hasError = RSAUtils.overedTime(target);
        if(StringUtil.isNotNull(hasError)){//验证失败
            request.getSession().setAttribute("errorStr", hasError);
            response.sendError(HttpServletResponse.SC_GONE);
            return false;
        }
		// 如果session中没有user对象
		if (null == request.getSession().getAttribute(WebConstants.CURRENT_USER)) {
			String requestedWith = request.getHeader("x-requested-with");
			// ajax请求
			if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
				response.setHeader("session-status", "timeout");
				response.getWriter().print(WebConstants.TIME_OUT);
			} else {
				// 普通页面请求
				response.sendRedirect(request.getContextPath() + "/");
			}
			return false;
		}
		return true;

	}
	
}
