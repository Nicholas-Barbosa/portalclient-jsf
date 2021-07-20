package com.portal.client.security.api;

public enum TokenType {

	Bearer("Bearer");

	private final String tokenType;

	private TokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenType() {
		return tokenType;
	}
}
