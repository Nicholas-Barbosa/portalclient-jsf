package com.farawaybr.portal.security.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.dto.LoginProtheusForm;
import com.farawaybr.portal.dto.ProtheusAuthenticationEndpointResponse;
import com.farawaybr.portal.resources.ProtheusApiUrlResolver;
import com.farawaybr.portal.security.api.APIsManager;
import com.farawaybr.portal.security.api.ProtheusApiData;
import com.farawaybr.portal.security.api.ProtheusApiEnviroment;
import com.farawaybr.portal.security.user.RepresentativeUser;
import com.farawaybr.portal.security.user.builder.RepresentativeUserBuilder;
import com.nicholas.jaxrsclient.RestClient;

@RequestScoped
public class ProtheusApiAuthenticationService implements AuthenticationService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233748924596132481L;
	private RestClient restClient;
	private ProtheusApiUrlResolver protheusApiUrlResolver;
	@Inject
	private Event<AuthenticateddEvent> authenticatedEvent;
	@Inject
	private APIsManager apisManger;

	@Inject
	public ProtheusApiAuthenticationService(RestClient restClient, ProtheusApiUrlResolver protheusApiUrlResolver) {
		super();
		this.restClient = restClient;
		this.protheusApiUrlResolver = protheusApiUrlResolver;
	}

	@Override
	public void authenticate(LoginProtheusForm loginForm) {
		// TODO Auto-generated method stub
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("grant_type", "password");
		queryParams.put("password", loginForm.getPassword());
		queryParams.put("username", loginForm.getUsername());
		String baseUrl = protheusApiUrlResolver.getUrl(loginForm.getCompanyEnv());
		String authenticationUrl = String.format("%s/%s", baseUrl, "api/oauth2/v1/token");
		ProtheusAuthenticationEndpointResponse authResponse = restClient.post(authenticationUrl,
				ProtheusAuthenticationEndpointResponse.class, queryParams, null, null, MediaType.APPLICATION_JSON);

		this.registerApi(
				(RepresentativeUser) RepresentativeUserBuilder.getInstance().withUsername(loginForm.getUsername())
						.withPassword(loginForm.getPassword().toCharArray()).build(),
				authResponse.getAccessToken(), authResponse.getRefreshToken(), "Bearer", loginForm.getCompanyEnv());
		authenticatedEvent.fire(new AuthenticateddEvent(loginForm.getCompanyEnv()));
		loginForm = null;
	}

	private void registerApi(RepresentativeUser user, String token, String refreshToken, String tokenPrefix,
			ProtheusApiEnviroment enviroment) {
		ProtheusApiData api = new ProtheusApiData(user, protheusApiUrlResolver.getUrl(enviroment),
				"api/oauth2/v1/token", "api/oauth2/v1/token", token, refreshToken, tokenPrefix);
		api.setAttribute("companyEnv", enviroment);
		apisManger.registerAuthenticatedService("PROTHEUS_API", api);
	}

	@Override
	public void refreshToken() {
		ProtheusApiData apiData = apisManger.getAPI("PROTHEUS_API");
		System.out.println("Refresh token " + apiData.getRefreshToken());
		ProtheusAuthenticationEndpointResponse refreshResponse = restClient.post(
				apisManger.buildEndpoint(apiData, apiData.getRefreshTokenUrl()),
				ProtheusAuthenticationEndpointResponse.class,
				Map.of("grant_type", "refresh_token", "refresh_token", apiData.getRefreshToken()), null, null,
				MediaType.APPLICATION_JSON);
		String newToken = refreshResponse.getAccessToken();
		String newRefreshToken = refreshResponse.getRefreshToken();
		apiData.setToken(newToken);
		apiData.setRefreshToken(newRefreshToken);

	}
}
