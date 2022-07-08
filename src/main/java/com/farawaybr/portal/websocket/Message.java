package com.farawaybr.portal.websocket;

import java.time.LocalDateTime;

public class Message {

	private final String content;
	private final LocalDateTime sendAt;
	private final String destinationId;

	public Message(String content, LocalDateTime sendAt, String destinationId) {
		super();
		this.content = content;
		this.sendAt = sendAt;
		this.destinationId = destinationId;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getSendAt() {
		return sendAt;
	}

	public String getDestinationId() {
		return destinationId;
	}

}
