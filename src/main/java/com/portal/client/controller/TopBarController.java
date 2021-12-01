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
	private static final String nsgLogoCss = "width:5.5vw;height:9.5vh;border-radius:50%";
	private static final String cdgLogoCss = "";
	private String currentLogoCss;

	@PostConstruct
	public void afterDI() {
		this.loadImgLogoCss();

	}

	public void loadImgLogoCss() {
		switch ((ProtheusCompanyApiEnv) protheusApiHelper.getSourceAPI().getAttribute("companyEnv")) {
		case NSG:
			this.image = "NSG.png";
			this.currentLogoCss = nsgLogoCss;
			break;
		case CDG:
			this.image = "Webp.net-resizeimage-gausslg.png";
			break;
		case SPG:
			this.image = "Webp.net-resizeimage-gausslg.png";
			break;
		}
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

	public String getCurrentLogoCss() {
		return currentLogoCss;
	}
}
