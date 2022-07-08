package com.farawaybr.portal.websocket.repo;

import com.farawaybr.portal.websocket.ChatWebSocketConnection;

public interface ChatConnectionsRepository {

	void putConnection(ChatWebSocketConnection connection);

	void removeConnection(ChatWebSocketConnection connection);
}
