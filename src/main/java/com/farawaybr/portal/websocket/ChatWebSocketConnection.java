package com.farawaybr.portal.websocket;

import java.time.LocalDateTime;

import javax.websocket.Session;

public class ChatWebSocketConnection {

	private final String httpSessionId;
	private final Session session;
	private final LocalDateTime createdAt;

	public ChatWebSocketConnection(String httpSessionId, Session session) {
		super();
		this.httpSessionId = httpSessionId;
		this.session = session;
		this.createdAt = LocalDateTime.now();
	}

	public String getHttpSessionId() {
		return httpSessionId;
	}

	public Session getSession() {
		return session;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
