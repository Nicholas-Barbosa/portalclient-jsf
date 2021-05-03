package com.portal.controller;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;

import com.portal.dto.LoginForm;
import com.portal.repository.AuthenticationRepository;
import com.portal.service.faces.FacesService;
import com.portal.service.view.HoldMessageView;

@RequestScoped
@Named
public class LoginController {

	private final HoldMessageView holdMessageView;

	private final AuthenticationRepository authenticationRepository;
	private final FacesService facesService;

	private LoginForm loginForm;
	private String headerDlgMessage;

	public LoginController() {
		this(null, null, null);
	}

	@Inject
	public LoginController(HoldMessageView holdMessageView, AuthenticationRepository authenticationRepository,
			FacesService facesService) {
		super();
		this.holdMessageView = holdMessageView;
		this.headerDlgMessage = this.holdMessageView.label("autenticando_usuario");
		this.loginForm = new LoginForm();
		this.authenticationRepository = authenticationRepository;
		this.facesService = facesService;
	}

	public String authenticate() {
		try {
			this.authenticationRepository.login(loginForm);
			return "BUDGET_PREVIEW";
		} catch (SocketTimeoutException e) {
			this.facesService.error(null, holdMessageView.label("socket_exception"),
					holdMessageView.label("socket_exception_detalhes"));
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			this.facesService.error(null, holdMessageView.label("connect_exception"),
					holdMessageView.label("connect_exception_detales"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			this.facesService.error(null, holdMessageView.label("respota_invalida_servidor"),
					holdMessageView.label("detalhes_reposta_invalida_servidor"));
		} catch (TimeoutException e) {
			this.facesService.error(null, holdMessageView.label("timeout_ler_response"),
					holdMessageView.label("timeout_ler_response_detalhes"));

		} catch (NotAuthorizedException e) {
			this.facesService.error(null, holdMessageView.label("nao_encontrado"),
					holdMessageView.label("usuario_nao_encontrado"));
		} catch (Exception e) {
			e.printStackTrace();
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
