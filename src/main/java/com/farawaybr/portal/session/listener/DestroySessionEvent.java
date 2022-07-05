package com.farawaybr.portal.session.listener;

import javax.servlet.http.HttpSession;

public class DestroySessionEvent {

	private HttpSession session;

	public DestroySessionEvent(HttpSession session) {
		super();
		this.session = session;
	}

	public HttpSession getSession() {
		return session;
	}
}
