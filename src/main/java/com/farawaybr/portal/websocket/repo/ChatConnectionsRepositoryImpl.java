package com.farawaybr.portal.websocket.repo;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;

import com.farawaybr.portal.websocket.ChatWebSocketConnection;

@ApplicationScoped
public class ChatConnectionsRepositoryImpl implements ChatConnectionsRepository {

	private final Map<String, ChatWebSocketConnection> connections = new ConcurrentHashMap<>();

	@Override
	public void putConnection(@ObservesAsync @WebSocketNewConnection ChatWebSocketConnection connection) {
		// TODO Auto-generated method stub
		System.out.println("put connection, http session id " + connection.getHttpSessionId());
		connections.put(connection.getHttpSessionId(), connection);
	}

	@Override
	public void removeConnection(@ObservesAsync @WebSocketClosedConnection ChatWebSocketConnection connection) {
		// TODO Auto-generated method stub
		connections.remove(connection.getHttpSessionId());
	}

	@Override
	public Optional<ChatWebSocketConnection> findByHttpSession(String httpSessionId) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(connections.get(httpSessionId));
	}

}
