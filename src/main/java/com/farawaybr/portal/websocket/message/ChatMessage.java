package com.farawaybr.portal.websocket.message;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ChatMessage {

	private final String content, interlocutor;
	private final LocalDateTime sendAt;
	private final String conversationId;
	private final ChatMessageType type;

	@JsonbCreator
	public ChatMessage(@JsonbProperty("content") String content,
			@JsonbProperty("conversation_id") String conversationId) {
		super();
		this.content = content;
		this.sendAt = LocalDateTime.now();
		this.conversationId = conversationId;
		this.type = ChatMessageType.INTERLOCUTOR;
		this.interlocutor = null;
	}

	public ChatMessage(String content, String conversationId, ChatMessageType type, String interlocutor) {
		super();
		this.content = content;
		this.sendAt = LocalDateTime.now();
		this.conversationId = conversationId;
		this.type = type;
		this.interlocutor = interlocutor;
	}

	public ChatMessage(String conversationId, String interlocutor, ChatMessageType type) {
		this(null, conversationId, type, interlocutor);
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getSendAt() {
		return sendAt;
	}

	public String getConversationId() {
		return conversationId;
	}

	public ChatMessageType getType() {
		return type;
	}

	public String getInterlocutor() {
		return interlocutor;
	}

	@Override
	public String toString() {
		return "ChatMessage [content=" + content + ", sendAt=" + sendAt + ", conversationId=" + conversationId + "]";
	}

}
