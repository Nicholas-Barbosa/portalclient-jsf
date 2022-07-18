package com.farawaybr.portal.websocket.service;

import com.farawaybr.portal.websocket.ChatWebSocketConnection;
import com.farawaybr.portal.websocket.message.ChatMessage;

public interface ChatService {

	String addConversation(String interlocutor1, String interlocutor2);

	void removeConversation(String conversationId);

	void sendMessage(ChatMessage message, ChatWebSocketConnection currentConnection);

}
