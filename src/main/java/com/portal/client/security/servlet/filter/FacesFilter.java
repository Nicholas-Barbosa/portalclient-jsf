package com.portal.client.security.servlet.filter;

import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.portal.client.security.UserSessionAPIManager;

@WebFilter(value = "/faces/*")
public class FacesFilter implements Filter {

	private final UserSessionAPIManager userPropertyHolder;

	private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<partial-response><redirect url=\"%s\"></redirect></partial-response>";


	@Inject
	public FacesFilter(UserSessionAPIManager userPropertyHolder) {
		super();
		this.userPropertyHolder = userPropertyHolder;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

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

	private String getPreviousPage(HttpServletRequest httpServletRequest) {
		String refererHeader = httpServletRequest.getHeader("Referer");
		return refererHeader == null ? null : URI.create(refererHeader).getPath();
	}
}
