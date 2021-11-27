package com.portal.client.controller;

import java.io.Serializable;
import java.util.Locale;

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
	private Locale clientLocale;

	@Override
	public void onException(Exception e) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		this.clientIp = request.getRemoteAddr();
		this.clientLocale = request.getLocale();
		System.out.println("clientLocale " + clientLocale);
	}

	public String getClientIp() {
		return clientIp;
	}

	public Locale getClientLocale() {
		return clientLocale;
	}
}
