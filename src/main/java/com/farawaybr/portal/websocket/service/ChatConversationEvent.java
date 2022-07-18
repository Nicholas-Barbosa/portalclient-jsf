package com.farawaybr.portal.websocket.service;

import com.farawaybr.portal.websocket.ChatConversation;

public class ChatConversationEvent {

	private final ChatConversation conversation;

	public ChatConversationEvent(ChatConversation conversation) {
		super();
		this.conversation = conversation;
	}

	public ChatConversation getConversation() {
		return conversation;
	}

}
