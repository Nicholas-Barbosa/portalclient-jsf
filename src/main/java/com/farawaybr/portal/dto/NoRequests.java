package com.farawaybr.portal.dto;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbProperty;

public class NoRequests {

	private int status;
	private LocalDateTime createdAt;
	private String cause;

	public NoRequests(String message) {
		this.status = 404;
		this.createdAt = LocalDateTime.now();
		this.cause = message;
	}

	@JsonbProperty
	public int getStatus() {
		return status;
	}

	@JsonbProperty
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@JsonbProperty
	public String getCause() {
		return cause;
	}

}
