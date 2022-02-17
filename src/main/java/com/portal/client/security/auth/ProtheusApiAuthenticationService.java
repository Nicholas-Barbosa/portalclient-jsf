package com.portal.client.security.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.RestClient;
import com.portal.client.dto.LoginProtheusForm;
import com.portal.client.dto.ProtheusAuthenticationEndpointResponse;
import com.portal.client.resources.ProtheusApiUrlResolver;
import com.portal.client.security.api.APIsManager;
import com.portal.client.security.api.ProtheusApiData;
import com.portal.client.security.api.ProtheusApiEnviroment;
import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.security.user.builder.RepresentativeUserBuilder;

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

	public void registerApi(RepresentativeUser user, String token, String refreshToken, String tokenPrefix,
			ProtheusApiEnviroment enviroment) {
		ProtheusApiData api = new ProtheusApiData(user, protheusApiUrlResolver.getUrl(enviroment), "/v1/token",
				"/v1/token", token, refreshToken, tokenPrefix);
		api.setAttribute("companyEnv", enviroment);
		apisManger.registerAuthenticatedService("PROTHEUS_API", api);
	}

	@Override
	public void refreshToken() {
		ProtheusApiData apiData = apisManger.getAPI("PROTHEUS_API");
		ProtheusAuthenticationEndpointResponse refreshResponse = restClient.post(apiData.getRefreshTokenUrl(),
				ProtheusAuthenticationEndpointResponse.class,
				Map.of("grant_type", "refresh_token", "refresh_token", apiData.getRefreshToken()), null, null,
				MediaType.APPLICATION_JSON);
		String newToken = refreshResponse.getAccessToken();
		String newRefreshToken = refreshResponse.getRefreshToken();
		apiData.setToken(newToken);
		apiData.setRefreshToken(newRefreshToken);

	}
}
