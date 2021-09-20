package com.portal.client.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.LoginForm;
import com.portal.client.dto.LoginGssResponseDTO;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.security.APIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.security.user.User;
import com.portal.client.security.user.builder.RepresentativeUserBuilder;

public class MainAuthenticationRepository implements AuthenticationRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233748924596132481L;
	private final RestClient restClient;
	private final APIManager userPropertyHolder;
	@EJB
	private ConfigPropertyResolver propertiesReader;

	public MainAuthenticationRepository() {
		this(null, null);
	}

	@Inject
	public MainAuthenticationRepository(RestClient restClient, APIManager userPropertyHolder) {
		super();
		this.restClient = restClient;
		this.userPropertyHolder = userPropertyHolder;

	}

	@Override
	public void login(LoginForm loginForm) {
		// TODO Auto-generated method stub
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("grant_type", "password");
		queryParams.put("password", loginForm.getPassword());
		queryParams.put("username", loginForm.getUsername());
		String currentEniviromentUrl = propertiesReader.getProperty("orcamento_api_url_prod");
		String loginUrl = String.format("%s/%s", currentEniviromentUrl, "api/oauth2/v1/token");

		LoginGssResponseDTO doPost = restClient.post(loginUrl, LoginGssResponseDTO.class, queryParams, null, null,
				MediaType.APPLICATION_JSON);
		User user = RepresentativeUserBuilder.getInstance().withUsername(loginForm.getUsername())
				.withPassword(loginForm.getPassword().toCharArray()).build();
		ServerAPI server = new ServerAPI(user, currentEniviromentUrl, "v1/token", doPost.getAccessToken(), "Bearer");

		userPropertyHolder.registerAuthenticatedService("ORCAMENTO_API", server);
		loginForm = null;
	}

}
