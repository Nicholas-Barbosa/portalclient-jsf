package com.farawaybr.portal.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.farawaybr.portal.repository.ConnectionSessionRepository;

@ServerEndpoint("/chat")
public class ChatEndpoint {

	private final Map<String, Session> avaliableSessions = new ConcurrentHashMap<>();

	@Inject
	private ConnectionSessionRepository connectionSessionRepository;

	@Inject
	private HttpSessionRequestK httpSessionRequestK;

	private String httpSessionId;

	@PostConstruct
	public void postContruct() {
		System.out.println("post construct chat endpoint ");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("pre destroy chat endpoint ");
	}

	@OnOpen
	public void onOpen(Session session) throws IOException {
		this.httpSessionId = httpSessionRequestK.getSession().getId();
		System.out.println("[SERVER]: Handshake successful!!!!! - Connected!!!!! - Session ID: " + session.getId()
				+ " http session id " + httpSessionId);
		if (connectionSessionRepository.isActive(httpSessionId)) {
			this.avaliableSessions.put(httpSessionId, session);
			return;
		}
		session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, "Http session does not exists!"));
	}

	@OnMessage
	public void processGreeting(String message, Session session) {
		System.out.println("Greeting received:" + message + "\n http session " + httpSessionId);
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("close session " + session.getId());
	}
}
