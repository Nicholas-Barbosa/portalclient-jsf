package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class LoginGssResponseDTO {

	private String accessToken;

	private String refreshToken;
	private String scope;

	private String tokenType;
	private Long expireIn;

	public LoginGssResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public LoginGssResponseDTO(@JsonbProperty("access_token") String accessToken,
			@JsonbProperty("refresh_token") String refreshToken, @JsonbProperty("scope") String scope,
			@JsonbProperty("token_type") String tokenType, @JsonbProperty("expires_in") Long expireIn) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.scope = scope;
		this.tokenType = tokenType;
		this.expireIn = expireIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(Long expireIn) {
		this.expireIn = expireIn;
	}

	@Override
	public String toString() {
		return "LoginGssResponseDTO [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", scope=" + scope
				+ ", tokenType=" + tokenType + ", expireIn=" + expireIn + "]";
	}

}
