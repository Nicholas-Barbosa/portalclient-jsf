package com.farawaybr.portal.websocket.service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.farawaybr.portal.websocket.ChatConversation;
import com.farawaybr.portal.websocket.ChatWebSocketConnection;
import com.farawaybr.portal.websocket.message.ChatMessage;
import com.farawaybr.portal.websocket.message.ChatMessageType;
import com.farawaybr.portal.websocket.repo.ChatConnectionsRepository;
import com.farawaybr.portal.websocket.repo.WebSocketClosedConnection;

@ApplicationScoped
public class ChatServiceImpl implements ChatService {

	private final Map<String, ChatConversation> conversations = new ConcurrentHashMap<>();

	@Inject
	private ChatConnectionsRepository chatConnectionsRepository;

	@Override
	public String addConversation(String interlocutor1, String interlocutor2) {
		// TODO Auto-generated method stub
		ChatWebSocketConnection opInterlocutor1 = chatConnectionsRepository.findByHttpSession(interlocutor1)
				.orElseThrow();
		ChatWebSocketConnection opInterlocutor2 = chatConnectionsRepository.findByHttpSession(interlocutor2)
				.orElseThrow();
		String id = this.generateConversationId(opInterlocutor1.getWSSessionId(), opInterlocutor2.getWSSessionId());
		ChatConversation conversation = new ChatConversation(id, opInterlocutor1, opInterlocutor2);
		if (this.conversations.putIfAbsent(id, conversation) == null)
			this.sendMessage(new ChatMessage(id, opInterlocutor1.getUser(), ChatMessageType.CONVERSATION_HANDSHAKE),
					opInterlocutor1);
		return id;
	}

	@Override
	public void removeConversation(String conversationId) {
		// TODO Auto-generated method stub
		this.conversations.remove(conversationId);
	}

	@Override
	public void sendMessage(ChatMessage message, ChatWebSocketConnection currentConnection) {
		Optional<ChatConversation> opCC = Optional.ofNullable(conversations.get(message.getConversationId()));

		opCC.ifPresentOrElse(cc -> {
			Session destination = cc.getInterlocutor1Session().equals(currentConnection.getSession())
					? cc.getInterlocutor2Session()
					: cc.getInterlocutor1Session();
			this.broadcast(message, destination);
		}, () -> {

		});

	}

	public void onChatSocketClosed(@ObservesAsync @WebSocketClosedConnection ChatWebSocketConnection connection) {
		conversations.values().removeIf(cc -> cc.containsInterlocutor(connection));
	}

	private String generateConversationId(String sessionId1, String sessionId2) {
		// TODO Auto-generated method stub
		return sessionId1 + "&" + sessionId2;
	}

	private void broadcast(ChatMessage message, Session destination) {
		try {
			destination.getBasicRemote().sendObject(message);
		} catch (IOException | EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
