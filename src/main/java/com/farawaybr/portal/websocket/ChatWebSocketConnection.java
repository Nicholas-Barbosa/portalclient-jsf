package com.farawaybr.portal.websocket;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.websocket.Session;

public class ChatWebSocketConnection {

	private final String httpSessionId, user;
	private Session session;
	private final LocalDateTime createdAt;

	public ChatWebSocketConnection(String httpSessionId, String user, Session session) {
		super();
		this.httpSessionId = httpSessionId;
		this.user = user;
		this.session = session;
		this.createdAt = LocalDateTime.now();
	}

	public String getHttpSessionId() {
		return httpSessionId;
	}

	public String getUser() {
		return user;
	}

	public String getWSSessionId() {
		return session.getId();
	}

	public Session getSession() {
		return session;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void replaceWSSession(Session newSession) {
		this.session = newSession;
	}

	@Override
	public int hashCode() {
		return Objects.hash(httpSessionId, session);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatWebSocketConnection other = (ChatWebSocketConnection) obj;
		return Objects.equals(httpSessionId, other.httpSessionId) && Objects.equals(session, other.session);
	}

}
