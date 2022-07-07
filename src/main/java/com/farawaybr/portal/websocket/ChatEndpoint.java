package com.farawaybr.portal.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.farawaybr.portal.repository.ConnectionSessionRepository;

@ServerEndpoint("/chat/{httpSessionId}")
public class ChatEndpoint {

	private final Map<String, Session> avaliableSessions = new ConcurrentHashMap<>();

	@Inject
	private ConnectionSessionRepository connectionSessionRepository;

	@OnOpen
	public void onOpen(Session session, @PathParam("httpSessionId") String httpSessionId) throws IOException {
		System.out.println("[SERVER]: Handshake successful!!!!! - Connected!!!!! - Session ID: " + session.getId());
		if (connectionSessionRepository.isActive(httpSessionId)) {
			this.avaliableSessions.put(httpSessionId, session);
			return;
		}
		session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, "Http session does not exists!"));
	}

	@OnMessage
	public void processGreeting(String message, Session session) {
		System.out.println("Greeting received:" + message);
	}

}
