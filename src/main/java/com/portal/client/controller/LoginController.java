package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import com.portal.client.dto.LoginForm;
import com.portal.client.repository.AuthenticationRepository;
import com.portal.client.service.ResourceBundleService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ProcessingExceptionFacesMessageHelper;

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
	private ProcessingExceptionFacesMessageHelper processingExceptionMessageHelper;

	@Inject
	public LoginController(AuthenticationRepository authenticationRepository,
			ResourceBundleService resourceBundleService,
			ProcessingExceptionFacesMessageHelper processingExceptionMessageHelper) {
		this.authenticationRepository = authenticationRepository;
		this.resourceBundleService = resourceBundleService;
		this.headerDlgMessage = this.resourceBundleService.getMessage("auteticando_usuario");
		this.loginForm = new LoginForm();
		this.processingExceptionMessageHelper = processingExceptionMessageHelper;
	}

	public String authenticate() {
		try {
			System.out.println("login!!!");
			this.authenticationRepository.login(loginForm);
			FacesUtils.addHeaderForResponse("ok", true);
			return "NEW_ORDER";
		} catch (NotAuthorizedException e) {
			FacesUtils.error(null, resourceBundleService.getMessage("usuario_nao_encontrado"), null);

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
