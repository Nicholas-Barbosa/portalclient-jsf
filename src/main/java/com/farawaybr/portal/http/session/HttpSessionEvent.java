package com.farawaybr.portal.http.session;

import javax.servlet.http.HttpSession;

public class HttpSessionEvent {

	private HttpSession session;

	public HttpSessionEvent(HttpSession session) {
		super();
		this.session = session;
	}

	public HttpSession getSession() {
		return session;
	}
}
