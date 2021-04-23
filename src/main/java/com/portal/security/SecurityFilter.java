package com.portal.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(value = "/faces/*")
public class SecurityFilter implements Filter {

	private final UserPropertyHolder userPropertyHolder;

	@Inject
	public SecurityFilter(UserPropertyHolder userPropertyHolder) {
		super();
		this.userPropertyHolder = userPropertyHolder;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (!userPropertyHolder.isAuthenticated()
				&& !httpRequest.getRequestURI().equals("/PortalAppClient/faces/login.xhtml")
				&& !httpRequest.getRequestURI().contains("resource")) {
			((HttpServletResponse) response).sendRedirect("login.xhtml");

		}
		chain.doFilter(request, response);

	}

}
