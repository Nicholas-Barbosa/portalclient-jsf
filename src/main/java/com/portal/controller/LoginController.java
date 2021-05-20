package com.portal.controller;

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

	/**
	 * 
	 */

	private final HoldMessageView holdMessageView;

	private final AuthenticationRepository authenticationRepository;
	private final FacesService facesService;
	private LoginForm loginForm;
	private String headerDlgMessage;
	private String previousPage;

	public LoginController() {
		this(null, null, null);
	}

	@Inject
	public LoginController(HoldMessageView holdMessageView, AuthenticationRepository authenticationRepository,
			FacesService facesService) {
		super();
		this.holdMessageView = holdMessageView;
		this.headerDlgMessage = this.holdMessageView.label("auteticando_usuario");
		this.loginForm = new LoginForm();
		this.authenticationRepository = authenticationRepository;
		this.facesService = facesService;
	}

	public String authenticate() {
		try {
			this.authenticationRepository.login(loginForm);
			return "BUDGET_PREVIEW";
		} catch (NotAuthorizedException e) {
			this.facesService.error(null, holdMessageView.label("nao_encontrado"),
					holdMessageView.label("usuario_nao_encontrado"));
		} catch (Exception e) {
			facesService.exceptionMessage().addMessageByException(null, e);
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

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public String getPreviousPage() {
		return previousPage;
	}
}
