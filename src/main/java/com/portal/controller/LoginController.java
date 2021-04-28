package com.portal.controller;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.LoginForm;
import com.portal.dto.LoginGssResponseDTO;
import com.portal.exception.ProcessingExceptionHandler;
import com.portal.security.UserPropertyHolder;
import com.portal.security.api.OAuth2ServiceApi;
import com.portal.security.api.ServiceApi;
import com.portal.security.api.TokenType;
import com.portal.service.view.HoldMessageView;

@RequestScoped
@Named
public class LoginController {

	private final UserPropertyHolder userPropertyHolder;
	private final AuthenticatedRestClient authenticatedRestClient;
	private final HoldMessageView holdMessageView;
	private final ProcessingExceptionHandler processingExceptionHandler;
	private LoginForm loginForm;
	private String headerDlgMessage;

	public LoginController() {
		this(null, null, null, null);
	}

	@Inject
	public LoginController(UserPropertyHolder userPropertyHolder,
			@OAuth2RestAuth AuthenticatedRestClient authenticatedRestClient, HoldMessageView holdMessageView,
			ProcessingExceptionHandler processingExceptionHandler) {
		super();
		this.userPropertyHolder = userPropertyHolder;
		this.authenticatedRestClient = authenticatedRestClient;
		this.holdMessageView = holdMessageView;
		this.headerDlgMessage = this.holdMessageView.label("autenticando_usuario");
		this.loginForm = new LoginForm();
		this.processingExceptionHandler = processingExceptionHandler;
	}

	public String doLogin() {
		try {
			Map<String, Object> queryParams = new HashMap<>();
			queryParams.put("grant_type", "password");
			queryParams.put("password", this.loginForm.getPassword());
			queryParams.put("username", this.loginForm.getUsername());
			LoginGssResponseDTO loginResponse = authenticatedRestClient.login(
					"http://192.168.0.246:8091/rest/api/oauth2/v1/token", null,
					null, LoginGssResponseDTO.class, queryParams);

			ServiceApi serivceApi = new OAuth2ServiceApi(this.loginForm.getUsername(),
					this.loginForm.getPassword().toCharArray(), "http://192.168.0.246:8091/rest",
					"/api/oauth2/v1/token", TokenType.Bearer, loginResponse.getAccessToken(),
					loginResponse.getRefreshToken(), "password", loginResponse.getScope(), LocalDateTime.now(),
					loginResponse.getExpireIn(), TimeUnit.SECONDS);

			userPropertyHolder.registerAuthenticatedService("GAUSS_ORCAMENTO", serivceApi);

			return "BUDGET_PREVIEW";
		} catch (NotAuthorizedException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					holdMessageView.label("resposta_servidor"), holdMessageView.label("usuario_nao_encontrado")));
			this.headerDlgMessage = holdMessageView.label("usuario_nao_encontrado");

			// TODO: handle exception

			// TODO: handle exception

		} catch (ProcessingException e) {
			System.out.println("Processing exception!");
			processingExceptionHandler.checkProcessingExceptionCauseAndAddMessage(e,null);

		} catch (Exception e) {
			FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							holdMessageView.label("erro_interno"),
							MessageFormat.format(holdMessageView.label("detalhes_erro_interno"), e.getMessage())));
			System.out.println("exception!");

		}
		return null;
	}

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public String getHeaderDlgMessage() {

		return headerDlgMessage;
	}

	public void setHeaderDlgMessage(String headerDlgMessage) {
		this.headerDlgMessage = headerDlgMessage;
	}
}
