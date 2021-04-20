package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class LoginGssResponseDTO {

	@JsonbProperty("access_token")
	private String accessToken;
	@JsonbProperty("refresh_token")
	private String refreshToken;
	private String scope;
	@JsonbProperty("token_type")
	private String tokenType;
	private Long expireIn;

	public LoginGssResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public LoginGssResponseDTO(String accessToken, String refreshToken, String scope, String tokenType, Long expireIn) {
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

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public Long getExpireIn() {
		return expireIn;
	}

	@Override
	public String toString() {
		return "LoginGssResponseDTO [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", scope=" + scope
				+ ", tokenType=" + tokenType + ", expireIn=" + expireIn + "]";
	}

}
