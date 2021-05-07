package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.Simple;
import com.portal.client.rest.RestClient;
import com.portal.dto.LoginForm;
import com.portal.dto.LoginGssResponseDTO;
import com.portal.security.UserPropertyHolder;
import com.portal.security.api.OAuth2ServiceApi;
import com.portal.security.api.ServiceApi;
import com.portal.security.api.TokenType;

public class MainAuthenticationRepository implements AuthenticationRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233748924596132481L;
	private final RestClient restClient;
	private final UserPropertyHolder userPropertyHolder;

	public MainAuthenticationRepository() {
		this(null, null);
	}

	@Inject
	public MainAuthenticationRepository(@Simple RestClient restClient, UserPropertyHolder userPropertyHolder) {
		super();
		this.restClient = restClient;
		this.userPropertyHolder = userPropertyHolder;
	}

	@Override
	public void login(LoginForm loginForm) throws SocketTimeoutException, ConnectException, IllegalArgumentException,
			TimeoutException, SocketException {
		// TODO Auto-generated method stub
		System.out.println("Login!");
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("grant_type", "password");
		queryParams.put("password", loginForm.getPassword());
		queryParams.put("username", loginForm.getUsername());
		LoginGssResponseDTO doPost = restClient.doPost("http://192.168.0.201:8090/rest/api/oauth2/v1/token",
				LoginGssResponseDTO.class, queryParams, null, null, MediaType.APPLICATION_JSON_TYPE);
		ServiceApi service = this.createServiceApi(loginForm.getUsername(), loginForm.getPassword(),
				"http://192.168.0.201:8090/rest", "v1/token", TokenType.Bearer, doPost.getAccessToken(),
				doPost.getRefreshToken(), "password", "default", 1l, TimeUnit.HOURS);
		System.out.println("service " + doPost);
		userPropertyHolder.registerAuthenticatedService("ORCAMENTO_API", service);

	}

	private ServiceApi createServiceApi(String username, String password, String basePath, String loginEndpoint,
			com.portal.security.api.TokenType token, String accessToken, String refreshToken, String grantType,
			String scope, Long duration, TimeUnit timeUnit) {
		return new OAuth2ServiceApi(username, password.toCharArray(), basePath, loginEndpoint, token, accessToken,
				refreshToken, grantType, scope, duration, timeUnit);
	}
}
