package com.portal.security;

import java.time.LocalDateTime;

public final class ServiceApi {

	private final String basePath;
	private final String token;
	private final LocalDateTime createdAt;
	private final LocalDateTime expireIn;
	private final AuthenticationType authenticationType;

	public ServiceApi(String basePath, String token, LocalDateTime createdAt, LocalDateTime expireIn,
			AuthenticationType authenticationType) {
		super();
		this.basePath = basePath;
		this.token = token;
		this.createdAt = createdAt;
		this.expireIn = expireIn;
		this.authenticationType = authenticationType;

	}

	public String getBasePath() {
		return basePath;
	}

	public String getToken() {
		return token;
	}

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getExpireIn() {
		return expireIn;
	}
}
