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
