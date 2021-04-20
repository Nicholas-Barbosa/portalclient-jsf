package com.portal.security.api;

public enum TokenType {

	BAERER("Baerer");

	private final String tokenType;

	private TokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenType() {
		return tokenType;
	}
}
