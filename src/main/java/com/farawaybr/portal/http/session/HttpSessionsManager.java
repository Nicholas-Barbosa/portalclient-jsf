package com.farawaybr.portal.http.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

@ApplicationScoped
public class HttpSessionsManager {

	private final Map<String, HttpSession> httpSessions = new ConcurrentHashMap<>();
	@Inject
	@DestroySessionEvent
	private Event<HttpSessionEvent> destroyHttpSessionEvent;

	public void onNewSession(@ObservesAsync @NewSessionEvent @Priority(0) javax.servlet.http.HttpSessionEvent event) {
		String clientIp = (String) event.getSession().getAttribute("clientIp");
		if (httpSessions.containsKey(clientIp)) {
			HttpSession remove = httpSessions.remove(clientIp);
			remove.invalidate();
		}
		httpSessions.put(clientIp, event.getSession());
	}

	public void onDestroySession(
			@ObservesAsync @DestroySessionEvent @Priority(0) javax.servlet.http.HttpSessionEvent event) {
		Optional<HttpSession> removedSession = Optional
				.ofNullable(httpSessions.remove(event.getSession().getAttribute("clientIp")));
		removedSession.ifPresent(HttpSession::invalidate);
	}
}
