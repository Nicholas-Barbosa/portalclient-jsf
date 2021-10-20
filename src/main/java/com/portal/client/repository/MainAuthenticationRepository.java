package com.portal.client.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.controller.ProtheusApiUrlHandler;
import com.portal.client.dto.LoginProtheusEndpointResponse;
import com.portal.client.dto.LoginProtheusForm;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.security.api.register.ProtheusApiRegister;
import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.security.user.builder.RepresentativeUserBuilder;

@ApplicationScoped
public class MainAuthenticationRepository implements AuthenticationRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233748924596132481L;
	private RestClient restClient;
	private ProtheusApiRegister protheusRegister;
	private ProtheusApiUrlHandler protheusApiUrlHandler;

	@Inject
	public MainAuthenticationRepository(RestClient restClient, ProtheusApiRegister protheusRegister,
			ProtheusApiUrlHandler protheusApiUrlHandler) {
		super();
		this.restClient = restClient;
		this.protheusRegister = protheusRegister;
		this.protheusApiUrlHandler = protheusApiUrlHandler;
	}

	@Override
	public void login(LoginProtheusForm loginForm) {
		// TODO Auto-generated method stub
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("grant_type", "password");
		queryParams.put("password", loginForm.getPassword());
		queryParams.put("username", loginForm.getUsername());
		String currentEniviromentUrl = protheusApiUrlHandler.getUrl(loginForm.getCompanyEnv());
		String loginUrl = String.format("%s/%s", currentEniviromentUrl, "api/oauth2/v1/token");
		LoginProtheusEndpointResponse loginResponse = restClient.post(loginUrl, LoginProtheusEndpointResponse.class,
				queryParams, null, null, MediaType.APPLICATION_JSON);
		protheusRegister.token(loginResponse.getAccessToken()).tokenPrefix("Bearer")
				.setUser((RepresentativeUser) RepresentativeUserBuilder.getInstance()
						.withUsername(loginForm.getUsername()).withPassword(loginForm.getPassword().toCharArray())
						.build())
				.companyEnv(loginForm.getCompanyEnv()).register();

		loginForm = null;
	}

}
