package com.farawaybr.portal.websocket;

import java.util.Objects;
import java.util.Optional;

import javax.websocket.Session;

public class ChatConversation {

	private final String id;
	private ChatWebSocketConnection interlocutor1, interlocutor2;

	public ChatConversation(String id, ChatWebSocketConnection interlocutor1, ChatWebSocketConnection interlocutor2) {
		super();
		this.id = id;
		this.interlocutor1 = interlocutor1;
		this.interlocutor2 = interlocutor2;
	}

	public String getId() {
		return id;
	}

	public ChatWebSocketConnection getInterlocutor1() {
		return interlocutor1;
	}

	public ChatWebSocketConnection getInterlocutor2() {
		return interlocutor2;
	}

	public String getInterlocutor1SessionId() {
		return interlocutor1.getSession().getId();
	}

	public String getInterlocutor2SessionId() {
		return interlocutor2.getSession().getId();
	}

	public Session getInterlocutor1Session() {
		return interlocutor1.getSession();
	}

	public Session getInterlocutor2Session() {
		return interlocutor2.getSession();
	}

	public Optional<ChatWebSocketConnection> getInterlocutorByHttpSessionId(String httpSessionId) {
		return containsHttpSession(httpSessionId)
				? interlocutor1.getHttpSessionId().equals(httpSessionId) ? Optional.of(interlocutor1)
						: Optional.of(interlocutor2)
				: Optional.empty();
	}

	public boolean containsHttpSession(String httpSession) {
		return interlocutor1.getHttpSessionId().equals(httpSession)
				|| interlocutor2.getHttpSessionId().equals(httpSession);
	}

	@Override
	public int hashCode() {
		return Objects.hash(interlocutor1, interlocutor2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatConversation other = (ChatConversation) obj;
		return Objects.equals(interlocutor1, other.interlocutor1) && Objects.equals(interlocutor2, other.interlocutor2);
	}

	public boolean containsInterlocutor(ChatWebSocketConnection connection) {
		// TODO Auto-generated method stub
		return interlocutor1.equals(connection) || interlocutor2.equals(connection);
	}

}
