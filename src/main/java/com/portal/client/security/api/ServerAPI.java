package com.portal.client.security.api;

import java.io.Serializable;
import java.util.Arrays;

public class ServerAPI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239012235740247278L;
	private final String username;
	private final char[] password;
	private final String baseURL;
	private final String loginEndpoint;
	private String token;
	private final String tokenPrefix;
	

	public ServerAPI(String username, char[] password, String basePath, String loginEndpoint, String token,
			String tokenPrefix) {
		super();
		this.username = username;
		this.password = password;
		this.baseURL = basePath;
		this.loginEndpoint = loginEndpoint;
		this.token = token;
		this.tokenPrefix = tokenPrefix;
		
	}

	public String getUsername() {
		return username;
	}

	public char[] getPassword() {
		return password;
	}

	public String getBasePath() {
		return baseURL;
	}

	public String getLoginEndpoint() {
		return loginEndpoint;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "ServerAPI [username=" + username + ", password=" + Arrays.toString(password) + ", baseURL=" + baseURL
				+ ", loginEndpoint=" + loginEndpoint + ", token=" + token + ", tokenPrefix=" + tokenPrefix + "]";
	}
	
	
}
