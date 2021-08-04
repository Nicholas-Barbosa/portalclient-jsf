package com.portal.client.service.route;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class Request {

	private LocalDateTime createdAt;
	private Locale locale;
	private String clientIp, serverName, requestUri, contextPath, method;

	public Request(HttpServletRequest request) {
		super();
		this.createdAt = LocalDateTime.now();
		this.locale = request.getLocale();
		this.clientIp = request.getRemoteAddr();
		this.serverName = request.getServerName();
		this.requestUri = request.getRequestURI();
		this.contextPath = request.getContextPath();
		this.method = request.getMethod();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getClientIp() {
		return clientIp;
	}

	public String getServerName() {
		return serverName;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public String getContextPath() {
		return contextPath;
	}

	
}
