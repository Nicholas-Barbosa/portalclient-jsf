package com.farawaybr.portal.websocket;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.farawaybr.portal.security.api.helper.ProtheusAPIHelper;

@WebFilter("/chat/*")
public class ChatHandShakeServletFilter implements Filter {

	@Inject
	private HttpSessionRequestK httpSessionRequestK;

	@Inject
	private ProtheusAPIHelper protheusAPIHelper;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		httpSessionRequestK.setSession(((HttpServletRequest) request).getSession());
		httpSessionRequestK.setUser(protheusAPIHelper.getUser().getName());
		chain.doFilter(request, response);
	}

}
