package com.farawaybr.portal.websocket;

import java.time.LocalDateTime;

public class MessageEvent {

	private LocalDateTime createdAt;
	private String content;

	public MessageEvent(LocalDateTime createdAt, String content) {
		super();
		this.createdAt = createdAt;
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getContent() {
		return content;
	}
}
