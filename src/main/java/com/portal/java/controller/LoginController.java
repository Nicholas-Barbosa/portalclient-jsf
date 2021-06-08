package com.portal.java.controller;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;

import com.portal.java.dto.LoginForm;
import com.portal.java.repository.AuthenticationRepository;
import com.portal.java.service.ResourceBundleService;
import com.portal.java.util.jsf.FacesUtils;
import com.portal.java.util.jsf.ResourceExceptionMessageHelper;

@RequestScoped
@Named
public class LoginController {

	/**
	 * 
	 */

	private final AuthenticationRepository authenticationRepository;
	private final ResourceBundleService resourceBundleService;
	private LoginForm loginForm;
	private String headerDlgMessage;
	private String previousPage;
	@EJB
	private ResourceExceptionMessageHelper processingExceptionMessageHelper;

	@Inject
	public LoginController(AuthenticationRepository authenticationRepository,
			ResourceBundleService resourceBundleService) {
		this.authenticationRepository = authenticationRepository;
		this.resourceBundleService = resourceBundleService;
		this.headerDlgMessage = this.resourceBundleService.getMessage("auteticando_usuario");
		this.loginForm = new LoginForm();

	}

	public String authenticate() {
		try {
			this.authenticationRepository.login(loginForm);
			return "BUDGET_PREVIEW";
		} catch (NotAuthorizedException e) {
			FacesUtils.error(null, resourceBundleService.getMessage("nao_encontrado"),
					resourceBundleService.getMessage("usuario_nao_encontrado"));

		} catch (SocketTimeoutException | ConnectException | TimeoutException e) {
			processingExceptionMessageHelper.displayMessage(e, null);
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
