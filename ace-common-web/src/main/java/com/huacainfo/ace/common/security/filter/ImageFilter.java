package com.huacainfo.ace.common.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageFilter implements Filter {
	
	private  String j_captcha="j_captcha";
	private  String portalPath="portalPath";
	private  String j_captcha_error="j_captcha_error";
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		request.getSession().removeAttribute(this.j_captcha_error);
		String r_captcha = request.getParameter(this.j_captcha);
		String s_captcha = (String) request.getSession().getAttribute(this.j_captcha);
		if (r_captcha.equals(s_captcha)) {
			request.getSession().removeAttribute(j_captcha_error);
			arg2.doFilter(request, response);
		} else {
			String portalPath=(String)request.getSession().getAttribute(this.portalPath);
			request.getSession().setAttribute(j_captcha_error,"验证码错误.");
			logger.debug("验证码{}错误,实际{}",r_captcha,s_captcha);
			request.getSession().removeAttribute(this.j_captcha);
			response.sendRedirect(portalPath+"/dynamic/portal/secruity/login.jsp");
		}
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
