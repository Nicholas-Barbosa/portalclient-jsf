package com.farawaybr.portal.security.servlet.filter;

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

import com.farawaybr.portal.security.api.APIsManager;
import com.farawaybr.portal.service.route.RequestTracker;

@WebFilter(value = "/faces/*")
public class FacesFilter implements Filter {

	private final APIsManager userPropertyHolder;
	private final RequestTracker requestTracker;

	private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

	@Inject
	public FacesFilter(APIsManager userPropertyHolder, RequestTracker requestTracker) {
		super();
		this.userPropertyHolder = userPropertyHolder;
		this.requestTracker = requestTracker;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		requestTracker.addRequest(httpRequest);
		if (!userPropertyHolder.isAuthenticated() && !httpRequest.getRequestURI().contains("resource")) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			String loginUrl = String.format("%s/%s", httpRequest.getContextPath(), "login.xhtml");
			if (isAjaxRequest(httpRequest)) {

				httpResponse.setContentType("text/html;charset=UTF-8");

				httpResponse.getWriter().format(AJAX_REDIRECT_XML, loginUrl);
				return;
			}
			httpResponse.sendRedirect(loginUrl);
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest httpRequest) {
		return "partial/ajax".equals(httpRequest.getHeader("Faces-Request"));
	}

}
