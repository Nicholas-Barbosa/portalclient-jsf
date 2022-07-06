package com.farawaybr.portal.security.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.dto.LoginProtheusForm;
import com.farawaybr.portal.dto.ProtheusAuthenticationEndpointResponse;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.resources.ProtheusApiUrlResolver;
import com.farawaybr.portal.security.api.APIsManager;
import com.farawaybr.portal.security.api.ProtheusApiData;
import com.farawaybr.portal.security.api.ProtheusApiEnviroment;
import com.farawaybr.portal.security.user.ProtheusUser;
import com.farawaybr.portal.security.user.builder.ProtheusUserBuilder;

@ApplicationScoped
public class ProtheusApiAuthenticationService implements AuthenticationService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233748924596132481L;
	@Inject
	private RestClient restClient;
	@Inject
	private ProtheusApiUrlResolver protheusApiUrlResolver;

	@Inject
	private APIsManager apiManager;

	@Override
	public void authenticate(LoginProtheusForm form) {
		// TODO Auto-generated method stub
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("grant_type", "password");
		queryParams.put("password", form.getPassword());
		queryParams.put("username", form.getUsername());
		String baseUrl = protheusApiUrlResolver.getUrl(form.getCompanyEnv());
		String authenticationUrl = String.format("%s/%s", baseUrl, "api/oauth2/v1/token");
		ProtheusAuthenticationEndpointResponse authResponse = restClient.post(authenticationUrl,
				ProtheusAuthenticationEndpointResponse.class, queryParams, null, null, MediaType.APPLICATION_JSON,
				null);
		this.registerApi(
				(ProtheusUser) ProtheusUserBuilder.getInstance().withUsername(form.getUsername())
						.withPassword(form.getPassword().toCharArray()).build(),
				authResponse.getAccessToken(), authResponse.getRefreshToken(), "Bearer", form.getCompanyEnv());
	}

	private void registerApi(ProtheusUser user, String token, String refreshToken, String tokenPrefix,
			ProtheusApiEnviroment enviroment) {
		ProtheusApiData api = new ProtheusApiData(user, protheusApiUrlResolver.getUrl(enviroment),
				"api/oauth2/v1/token", "api/oauth2/v1/token", token, refreshToken, tokenPrefix);
		api.setAttribute("companyEnv", enviroment);
		apiManager.registerAuthenticatedService("PROTHEUS_API", api);
	}

	@Override
	public void refreshToken() {
		ProtheusApiData apiData = apiManager.getAPI("PROTHEUS_API");
		ProtheusAuthenticationEndpointResponse refreshResponse = restClient.post(
				apiManager.buildEndpoint(apiData, apiData.getRefreshTokenUrl()),
				ProtheusAuthenticationEndpointResponse.class,
				Map.of("grant_type", "refresh_token", "refresh_token", apiData.getRefreshToken()), null, null,
				MediaType.APPLICATION_JSON, null);
		String newToken = refreshResponse.getAccessToken();
		String newRefreshToken = refreshResponse.getRefreshToken();
		apiData.setToken(newToken);
		apiData.setRefreshToken(newRefreshToken);

	}
}
