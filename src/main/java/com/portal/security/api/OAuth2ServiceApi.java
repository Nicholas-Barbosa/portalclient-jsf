package com.portal.security.api;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class OAuth2ServiceApi extends ServiceApi {

	private final String token;
	private final String refreshToken;
	private final String grantType;
	private final String scope;
	private final LocalDateTime createdAt;
	private LocalDateTime expireIn;
	private final TimeUnit timeUnit;

	public OAuth2ServiceApi(String username, char[] password, String basePath, String loginEndpoint,
			TokenType tokenType, String token, String refreshToken, String grantType, String scope,
			LocalDateTime createdAt, Long duration, TimeUnit timeUnit) {
		super(username, password, basePath, loginEndpoint, tokenType);
		this.token = token;
		this.refreshToken = refreshToken;
		this.grantType = grantType;
		this.scope = scope;
		this.createdAt = createdAt;
		this.expireIn = cronExpireIn();
		this.timeUnit = timeUnit;
	}

	public String getToken() {
		return token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getGrantType() {
		return grantType;
	}

	public String getScope() {
		return scope;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getExpireIn() {
		return expireIn;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	private final LocalDateTime cronExpireIn(Long expireIn) {

		switch (timeUnit) {
		case MINUTES:
			return LocalDateTime.now().minusMinutes(expireIn);

		case DAYS:
			return LocalDateTime.now().minusDays(expireIn);
		case HOURS:
			return LocalDateTime.now().minusHours(expireIn);
		case MICROSECONDS:

			return null;
		case MILLISECONDS:
			return null;
		case NANOSECONDS:
			return null;
		case SECONDS:
			return null;

		default:
			throw new IllegalArgumentException();

		}

	}
}
