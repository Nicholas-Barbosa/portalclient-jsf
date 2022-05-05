package com.farawaybr.portal.security.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.farawaybr.portal.security.user.RepresentativeUser;

public class ProtheusApiData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239012235740247278L;

	private final String baseUrl;
	private final String authUrl;
	private final String refreshTokenUrl;
	private String token,refreshToken;
	private final String tokenPrefix;
	private RepresentativeUser userData;
	private final Map<String, Object> attributes;

	public ProtheusApiData(RepresentativeUser user, String baseUrl, String loginEndpoint, String refreshTokenUrl, String token,String refreshToken,
			String tokenPrefix) {
		super();
		this.userData = user;
		this.baseUrl = baseUrl;
		this.authUrl = loginEndpoint;
		this.refreshTokenUrl = refreshTokenUrl;
		this.token = token;
		this.refreshToken = refreshToken;
		this.tokenPrefix = tokenPrefix;
		attributes = new HashMap<>();
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getLoginEndpoint() {
		return authUrl;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public RepresentativeUser getLoggedUser() {
		return userData;
	}

	public void setUser(RepresentativeUser user) {
		this.userData = user;
	}

	public void setAttribute(String key, Object data) {
		attributes.put(key, data);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public String getRefreshTokenUrl() {
		return refreshTokenUrl;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	@Override
	public String toString() {
		return "ApiData [baseUrl=" + baseUrl + ", loginEndpoint=" + authUrl + ", token=" + token + ", tokenPrefix="
				+ tokenPrefix + ", userData=" + userData + ", attributes=" + attributes + "]";
	}

}
