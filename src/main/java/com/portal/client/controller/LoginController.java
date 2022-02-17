package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;

import com.portal.client.dto.LoginProtheusForm;
import com.portal.client.exceptionhandler.netowork.NetworkExceptionJoinPointCut;
import com.portal.client.security.auth.AuthenticationService;
import com.portal.client.service.ResourceBundleService;
import com.portal.client.util.jsf.FacesUtils;

@RequestScoped
@Named
@NetworkExceptionJoinPointCut
public class LoginController {

	/**
	 * 
	 */

	private final AuthenticationService authenticationRepository;
	private final ResourceBundleService resourceBundleService;
	private LoginProtheusForm loginForm;
	private String headerDlgMessage;
	private String previousPage;

	@Inject
	public LoginController(AuthenticationService authenticationRepository,
			ResourceBundleService resourceBundleService
			) {
		this.authenticationRepository = authenticationRepository;
		this.resourceBundleService = resourceBundleService;
		this.headerDlgMessage = this.resourceBundleService.getMessage("auteticando_usuario");
		this.loginForm = new LoginProtheusForm();
	}

	public String authenticate() {
		try {
			this.authenticationRepository.authenticate(loginForm);
			FacesUtils.addHeaderForResponse("ok", true);
			return "NEW_ORDER";
		} catch (NotAuthorizedException e) {
			FacesUtils.error(null, resourceBundleService.getMessage("usuario_nao_encontrado"), null);

		}
		return null;

	}

	public LoginProtheusForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginProtheusForm loginForm) {
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
