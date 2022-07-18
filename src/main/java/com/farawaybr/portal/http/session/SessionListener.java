package com.farawaybr.portal.http.session;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class SessionListener implements HttpSessionListener {

	@Inject
	@DestroySessionEvent
	private Event<HttpSessionEvent> destroySessionEvent;

	@Inject
	@NewSessionEvent
	private Event<HttpSessionEvent> newSessionEvent;

	@Inject
	private HttpServletRequest request;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		String clientIp = request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : request.getRemoteAddr();
		se.getSession().setAttribute("clientIp", clientIp);
		newSessionEvent.fireAsync(se);
		System.out.println("new session has been created");
		se.getSession().setMaxInactiveInterval(1200);

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("session destroyed!");
		destroySessionEvent.fireAsync(se);
	}
}
