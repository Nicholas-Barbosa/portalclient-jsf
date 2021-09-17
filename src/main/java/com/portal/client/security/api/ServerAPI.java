package com.portal.client.security.api;

import java.io.Serializable;

import com.portal.client.security.user.User;

public class ServerAPI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239012235740247278L;

	private final String baseURL;
	private final String loginEndpoint;
	private String token;
	private final String tokenPrefix;
	private final User userData;

	public ServerAPI(User user, String basePath, String loginEndpoint, String token, String tokenPrefix) {
		super();
		this.userData = user;
		this.baseURL = basePath;
		this.loginEndpoint = loginEndpoint;
		this.token = token;
		this.tokenPrefix = tokenPrefix;

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

	public User getUserData() {
		return userData;
	}


}
