package com.portal.client.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import com.portal.client.security.user.User;
import com.portal.client.service.RepresentativeService;
import com.portal.client.util.jsf.FacesUtils;

@Named
@SessionScoped
public class UserTopBarController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6071965638031666160L;

	@Inject
	private RepresentativeService service;

	private User user;

	public void loadUserData() {
		try {
			user = service.getAdditionalData();
		} catch (ProcessingException e) {
			FacesUtils.fatal(null, "Não foi possível buscar informações sobre o representante.", null, "growl");
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}
}
