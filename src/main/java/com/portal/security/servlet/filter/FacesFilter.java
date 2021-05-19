package com.portal.security.servlet.filter;

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

import com.portal.security.UserPropertyHolder;

@WebFilter(value = "/faces/*")
public class FacesFilter implements Filter {

	private final UserPropertyHolder userPropertyHolder;

	public FacesFilter() {
		this(null);
	}

	@Inject
	public FacesFilter(UserPropertyHolder userPropertyHolder) {
		super();
		this.userPropertyHolder = userPropertyHolder;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (!userPropertyHolder.isAuthenticated() && !httpRequest.getRequestURI().contains("/faces/login.xhtml")
				&& !httpRequest.getRequestURI().contains("resource")) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			if (isAjaxRequest(httpRequest)) {
				httpResponse.setHeader("redirect-to-login", "yes");
				chain.doFilter(httpRequest, response);
				return;
			}
			httpResponse.sendRedirect("login.xhtml");

		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest httpRequest) {
		String header = httpRequest.getHeader("X-Requested-With");
		return header != null ? header.contains("XMLHttpRequest") : false;
	}
}
