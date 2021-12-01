package com.portal.client.controller.components;

import java.io.Serializable;
import java.net.SocketTimeoutException;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.portal.client.exceptionhandler.netowork.NetworkExceptionObserver;

@SessionScoped
@Named
public class OnNetworkExceptionController implements NetworkExceptionObserver, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5355380388116956853L;

	private String clientIp;
	private String errorMessage;

	public void throwScoketException() throws SocketTimeoutException {
		this.onException(new SocketTimeoutException("connect"));
		throw new SocketTimeoutException("connect");
	}

	@Override
	public void onException(Exception e) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		this.clientIp = request.getRemoteAddr();
		this.errorMessage = e.getMessage().contains("connect")
				? "Não foi possível estabelecer uma conexão com o servidor a tempo. Tente novamente"
				: "O servidor não respondeu a esta requisição a tempo. Tente novamente mais tarde.";
	}

	public String getClientIp() {
		return clientIp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
