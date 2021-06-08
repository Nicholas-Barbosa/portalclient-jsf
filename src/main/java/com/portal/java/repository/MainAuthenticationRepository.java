package com.portal.java.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.java.cdi.qualifier.Simple;
import com.portal.java.client.rest.RestClient;
import com.portal.java.dto.LoginForm;
import com.portal.java.dto.LoginGssResponseDTO;
import com.portal.java.properties.PropertiesReader;
import com.portal.java.security.UserManagerProperties;
import com.portal.java.security.api.ExternalApiResource;
import com.portal.java.security.api.ExternalOAuth2ApiResource;
import com.portal.java.security.api.TokenType;

public class MainAuthenticationRepository implements AuthenticationRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233748924596132481L;
	private final RestClient restClient;
	private final UserManagerProperties userPropertyHolder;
	@EJB
	private PropertiesReader propertiesReader;

	public MainAuthenticationRepository() {
		this(null, null);
	}

	@Inject
	public MainAuthenticationRepository(@Simple RestClient restClient, UserManagerProperties userPropertyHolder) {
		super();
		this.restClient = restClient;
		this.userPropertyHolder = userPropertyHolder;

	}

	@Override
	public void login(LoginForm loginForm)throws SocketTimeoutException, ConnectException, TimeoutException {
		// TODO Auto-generated method stub
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("grant_type", "password");
		queryParams.put("password", loginForm.getPassword());
		queryParams.put("username", loginForm.getUsername());

		String currentEniviromentUrl = propertiesReader.getProperty("orcamento_api_url_teste");
		String loginUrl = String.format("%s/%s", currentEniviromentUrl, "api/oauth2/v1/token");

		LoginGssResponseDTO doPost = restClient.post(loginUrl, LoginGssResponseDTO.class, queryParams, null, null,
				MediaType.APPLICATION_JSON);
		ExternalApiResource service = this.createServiceApi(loginForm.getUsername(), loginForm.getPassword(),
				currentEniviromentUrl, "v1/token", TokenType.Bearer, doPost.getAccessToken(), doPost.getRefreshToken(),
				"password", "default", 1l, TimeUnit.HOURS);
		userPropertyHolder.registerAuthenticatedService("ORCAMENTO_API", service);

	}

	private ExternalApiResource createServiceApi(String username, String password, String basePath, String loginEndpoint,
			com.portal.java.security.api.TokenType token, String accessToken, String refreshToken, String grantType,
			String scope, Long duration, TimeUnit timeUnit) {
		return new ExternalOAuth2ApiResource(username, password.toCharArray(), basePath, loginEndpoint, token, accessToken,
				refreshToken, grantType, scope, duration, timeUnit);
	}
}
