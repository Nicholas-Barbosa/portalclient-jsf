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

@WebFilter("/chat/*")
public class ChatHandShakeFilter implements Filter {

	@Inject
	private HttpSessionRequestK httpSessionRequestK;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		httpSessionRequestK.setSession(((HttpServletRequest) request).getSession());
		chain.doFilter(request, response);
	}

}
