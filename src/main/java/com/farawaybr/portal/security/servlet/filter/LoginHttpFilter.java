package com.farawaybr.portal.security.servlet.filter;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.farawaybr.portal.security.api.APIsManager;

/**
 * Servlet Filter implementation class LoginHttpFilter
 */
@WebFilter("/auth/*")
public class LoginHttpFilter extends HttpFilter implements Filter {

	@Inject
	private APIsManager apiManager;
	@Inject
	private HttpSession httpSession;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public LoginHttpFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		if (apiManager.isAuthenticated(httpSession.getId())) {
			String loginUrl = String.format("%s/faces/budget/%s", ((HttpServletRequest) request).getContextPath(), "new.xhtml");
			((HttpServletResponse) response).sendRedirect(loginUrl);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
