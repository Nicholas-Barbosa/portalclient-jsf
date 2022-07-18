package com.farawaybr.portal.websocket.repo;

import java.util.Optional;

import com.farawaybr.portal.websocket.ChatWebSocketConnection;

public interface ChatConnectionsRepository {

	void putConnection(ChatWebSocketConnection connection);

	void removeConnection(ChatWebSocketConnection connection);

	Optional<ChatWebSocketConnection> findByHttpSession(String httpSessionId);
}
