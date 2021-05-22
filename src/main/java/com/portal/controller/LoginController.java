package com.portal.controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import com.portal.dto.LoginForm;
import com.portal.jsf.faces.FacesHelper;
import com.portal.jsf.faces.ProcessingExceptionMessageHelper;
import com.portal.repository.AuthenticationRepository;
import com.portal.service.ResourceBundleService;

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
	private ProcessingExceptionMessageHelper processingExceptionMessageHelper;

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
			FacesHelper.error(null, resourceBundleService.getMessage("nao_encontrado"),
					resourceBundleService.getMessage("usuario_nao_encontrado"));

		} catch (ProcessingException e) {
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
