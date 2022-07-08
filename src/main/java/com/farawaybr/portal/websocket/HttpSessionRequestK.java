package com.farawaybr.portal.websocket;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpSession;

@RequestScoped
public class HttpSessionRequestK {

	private HttpSession session;


	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
}
