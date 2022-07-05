package com.farawaybr.portal.security;

public class NotAuthorizedExceptionEvent {

	private String token;

	public NotAuthorizedExceptionEvent(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
