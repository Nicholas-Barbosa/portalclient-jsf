package com.portal.client.security.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.portal.client.security.user.User;

public class ServerAPI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239012235740247278L;

	private final String baseUrl;
	private final String loginEndpoint;
	private String token;
	private final String tokenPrefix;
	private User userData;
	private final Map<String, Object> attributes;

	public ServerAPI(User user, String baseUrl, String loginEndpoint, String token, String tokenPrefix) {
		super();
		this.userData = user;
		this.baseUrl = baseUrl;
		this.loginEndpoint = loginEndpoint;
		this.token = token;
		this.tokenPrefix = tokenPrefix;
		attributes = new HashMap<>();
	}

	public String getBaseUrl() {
		return baseUrl;
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

	public void setUser(User user) {
		this.userData = user;
	}

	public void setAttribute(String key, Object data) {
		attributes.put(key, data);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}
}
