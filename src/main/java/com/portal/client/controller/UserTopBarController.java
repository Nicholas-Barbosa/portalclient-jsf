package com.portal.client.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.security.user.User;
import com.portal.client.service.RepresentativeService;

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
		user = service.getAdditionalData();
	}

	public User getUser() {
		return user;
	}
}
