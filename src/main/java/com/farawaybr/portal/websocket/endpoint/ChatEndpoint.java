package com.farawaybr.portal.websocket.endpoint;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.farawaybr.portal.repository.ConnectionSessionRepository;
import com.farawaybr.portal.websocket.ChatWebSocketConnection;
import com.farawaybr.portal.websocket.HttpSessionRequestK;
import com.farawaybr.portal.websocket.JsonEncoder;
import com.farawaybr.portal.websocket.message.ChatMessage;
import com.farawaybr.portal.websocket.repo.WebSocketNewConnection;
import com.farawaybr.portal.websocket.service.ChatService;
import com.farawaybr.portal.websocket.repo.WebSocketClosedConnection;

@ServerEndpoint(value = "/chat", encoders = JsonEncoder.class, decoders = JsonEncoder.class)
public class ChatEndpoint {

	@Inject
	private ConnectionSessionRepository connectionSessionRepository;

	@Inject
	private HttpSessionRequestK httpSessionRequestK;

	private ChatWebSocketConnection connection;

	@Inject
	@WebSocketNewConnection
	private Event<ChatWebSocketConnection> newConnectionEvent;

	@Inject
	@WebSocketClosedConnection
	private Event<ChatWebSocketConnection> closedConnectionEvent;

	@Inject
	private ChatService chatService;

	@OnOpen
	public void onOpen(Session session) throws IOException {
		this.connection = new ChatWebSocketConnection(httpSessionRequestK.getSession().getId(),
				httpSessionRequestK.getUser(), session);
		System.out.println("[SERVER]: Handshake successful!!!!! - Connected!!!!! - Session ID: " + session.getId()
				+ " http session id " + connection.getHttpSessionId());
		if (connectionSessionRepository.isActive(this.connection.getHttpSessionId())) {
			newConnectionEvent.fireAsync(this.connection);
			return;
		}
		session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, "Http session does not exists!"));
	}

	@OnMessage
	public void onMessage(ChatMessage message, Session session) {
		chatService.sendMessage(message, connection);
	}

	@OnClose
	public void onClose(Session session) {
		closedConnectionEvent.fireAsync(this.connection);
	}
}
