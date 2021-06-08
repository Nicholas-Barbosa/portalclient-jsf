package com.portal.java.security.api;

import java.io.Serializable;

public class ExternalApiResource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239012235740247278L;
	private final String username;
	private final char[] password;
	private final String basePath;
	private final String loginEndpoint;
	private final TokenType tokenType;

	public ExternalApiResource(String username, char[] password, String basePath, String loginEndpoint, TokenType tokenType) {
		super();
		this.username = username;
		this.password = password;
		this.basePath = basePath;
		this.loginEndpoint = loginEndpoint;
		this.tokenType = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public char[] getPassword() {
		return password;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getLoginEndpoint() {
		return loginEndpoint;
	}

	public TokenType getTokenType() {
		return tokenType;
	}	
}
