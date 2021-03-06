package com.farawaybr.portal.jsf.controller;

import java.net.ConnectException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;

import com.farawaybr.portal.dto.LoginProtheusForm;
import com.farawaybr.portal.exceptionhandler.netowork.NetworkExceptionJoinPointCut;
import com.farawaybr.portal.security.auth.JsfAuthenticationFacade;
import com.farawaybr.portal.util.jsf.FacesUtils;

@RequestScoped
@Named
@NetworkExceptionJoinPointCut
public class LoginController {

	@Inject
	private JsfAuthenticationFacade facade;
	@Inject
	private HttpServletRequest request;
	private LoginProtheusForm loginForm = new LoginProtheusForm();;
	private String headerDlgMessage;
	private String previousPage;

	public String authenticate() {
		try {
			facade.authenticate(loginForm);
			FacesUtils.addHeaderForResponse("ok", true);
			return "NEW_ORDER";
		} catch (NotAuthorizedException e) {
			System.out.println("not authorized");
			FacesUtils
					.error(null, "Usuário não encontrado, em 5 tentativas, haverá bloqueio.",
							"Verifique se " + loginForm.getUsername() + " realmente existe no ambiente "
									+ loginForm.getEnvironment() + " Protheus e se seu usuário não está bloquado.",
							"messages");

		} catch (Exception e) {
			if (e.getCause() instanceof ConnectException)
				FacesUtils.error(null, "Connection timed out", "Servidor fora do ar", "messages");
			if (e.getCause() instanceof NotAuthorizedException)
				FacesUtils.error(null, "Usuário não encontrado", "Verifique se " + loginForm.getUsername()
						+ " realmente existe no ambiente " + loginForm.getEnvironment(), "messages");
			e.printStackTrace();
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
