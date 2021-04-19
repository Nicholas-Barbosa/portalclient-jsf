package com.portal.security;

public enum AuthenticationType {

	BAERER("Barer");

	private final String type;

	private AuthenticationType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
