package com.portal.client.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.service.RepresentativeService;
import com.portal.client.util.jsf.FacesUtils;

@Named
@SessionScoped
public class UserDataController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6071965638031666160L;

	@Inject
	private RepresentativeService service;
	private boolean loadedUserData;
	private RepresentativeUser user;

	public void loadUserData() {
		if (!loadedUserData)
			try {
				this.loadedUserData = true;
				user = service.find();
				switch (user.getType()) {
				case INTERNO:
					FacesUtils.executeScript("PF('dlgSetInternalSaleType').show()");
					break;

				default:
					break;
				}
			} catch (ProcessingException e) {
				FacesUtils.fatal(null, "Não foi possível buscar informações sobre o representante.", null, "growl");
				e.printStackTrace();
			}
	}

	public RepresentativeUser getUser() {
		return user;
	}

}
