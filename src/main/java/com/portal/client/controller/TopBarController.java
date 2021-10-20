package com.portal.client.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import com.portal.client.security.api.ProtheusCompanyApiEnv;
import com.portal.client.security.api.helper.APIHelper;
import com.portal.client.security.user.User;
import com.portal.client.service.RepresentativeService;
import com.portal.client.util.jsf.FacesUtils;

@Named
@SessionScoped
public class TopBarController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6071965638031666160L;

	@Inject
	private RepresentativeService service;
	@Inject
	private APIHelper protheusApiHelper;
	private boolean loadedUserData;
	private String image;
	private User user;

	@PostConstruct
	public void afterDI() {
		this.image = protheusApiHelper.getSourceAPI().getAttribute("companyEnv").equals(ProtheusCompanyApiEnv.GAUSS)
				? "Webp.net-resizeimage-gausslg.png"
				: "LOGOMARCA NSG.webp";
	}

	public void loadUserData() {
		if (!loadedUserData)
			try {
				this.loadedUserData = true;
				user = service.getAdditionalData();
			} catch (ProcessingException e) {
				FacesUtils.fatal(null, "Não foi possível buscar informações sobre o representante.", null, "growl");
				e.printStackTrace();
			}
	}

	public User getUser() {
		return user;
	}

	public String getImage() {
		return image;
	}
}
