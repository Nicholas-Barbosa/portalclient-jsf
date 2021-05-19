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

	private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

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
		if (!userPropertyHolder.isAuthenticated() && !httpRequest.getRequestURI().contains("resource")) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if (isAjaxRequest(httpRequest)) {
				// httpResponse.setHeader("redirect-to-login", "yes");
				// chain.doFilter(httpRequest, response);
				httpResponse.setContentType("text/html;charset=UTF-8");
				httpResponse.getWriter().format(AJAX_REDIRECT_XML, httpRequest.getContextPath() + "/login.xhtml");
				return;
			}
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest httpRequest) {
		return "partial/ajax".equals(httpRequest.getHeader("Faces-Request"));
	}
}
