package com.portal.service;

import java.time.LocalDateTime;

public final class ServicesApis {

	private final String basePath;
	private final String token;
	private final Long expireTime;
	private final LocalDateTime createdAt;
	public ServicesApis(String basePath, String token, Long expireTime, LocalDateTime createdAt) {
		super();
		this.basePath = basePath;
		this.token = token;
		this.expireTime = expireTime;
		this.createdAt = createdAt;
	}
	public String getBasePath() {
		return basePath;
	}
	public String getToken() {
		return token;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	
}
