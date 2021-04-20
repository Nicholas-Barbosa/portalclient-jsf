package com.portal.controller;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;

import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.LoginGssResponseDTO;
import com.portal.security.UserPropertyHolder;
import com.portal.security.api.OAuth2ServiceApi;
import com.portal.security.api.ServiceApi;
import com.portal.security.api.TokenType;

@RequestScoped
@Named
public class LoginController {

	private final UserPropertyHolder userPropertyHolder;
	private final AuthenticatedRestClient authenticatedRestClient;
	private String username, password;

	public LoginController() {
		this(null, null);
	}

	@Inject
	public LoginController(UserPropertyHolder userPropertyHolder, AuthenticatedRestClient authenticatedRestClient) {
		super();
		this.userPropertyHolder = userPropertyHolder;
		this.authenticatedRestClient = authenticatedRestClient;
	}

	public String doLogin() {
		System.out.println(
				"username " + FacesContext.getCurrentInstance().getExternalContext().getInitParameter("username"));
		try {
			LoginGssResponseDTO loginResponse = authenticatedRestClient.login(
					"http://192.168.0.246:8091/rest/api/oauth2/v1/token?grant_type={0}&password={1}&username={2}", null,
					null, LoginGssResponseDTO.class, "password", this.password, this.username);

			ServiceApi serivceApi = new OAuth2ServiceApi(username, password.toCharArray(),
					"http://192.168.0.246:8091/rest", "/api/oauth2/v1/token", TokenType.BAERER,
					loginResponse.getAccessToken(), loginResponse.getRefreshToken(), "password",
					loginResponse.getScope(), LocalDateTime.now(), loginResponse.getExpireIn(), TimeUnit.SECONDS);
			userPropertyHolder.registerAuthenticatedService("GAUSS_ORCAMENTO", serivceApi);
			return "BUDGET_PREVIEW";
		} catch (Exception e) {
			// TODO: handle exception
			if (e instanceof NotAuthorizedException) {

			}

			e.printStackTrace();
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
