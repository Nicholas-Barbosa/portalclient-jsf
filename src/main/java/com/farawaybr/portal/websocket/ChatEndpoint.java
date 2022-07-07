package com.farawaybr.portal.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.PathParam;

//@ServerEndpoint("/chat/{httpSessionId}")
public class ChatEndpoint {

	private final Map<String, Session> avaliableSessions = new ConcurrentHashMap<>();

//	@OnOpen
	public void onOpen(Session session, @PathParam("httpSessionId") String httpSessionId) {
		System.out.println("[SERVER]: Handshake successful!!!!! - Connected!!!!! - Session ID: " + session.getId());
	}

//	@OnMessage
	public void processGreeting(String message, Session session) {
		System.out.println("Greeting received:" + message);
	}

}
