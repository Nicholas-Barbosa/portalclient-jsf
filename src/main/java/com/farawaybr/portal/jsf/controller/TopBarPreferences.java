package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.security.api.ProtheusApiEnviroment;
import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.security.auth.LoggedEvent;
import com.farawaybr.portal.security.user.ProtheusUser;

@SessionScoped
@Named
public class TopBarPreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5243438532867147574L;
	private static final String nsgLogoCss = "width:5.5vw;height:9.5vh;border-radius:50%";
	private static final String spgLogoCss = "width:4.5vw;height:6vh";
	private String image;
	private String currentLogoCss;
	private ProtheusUser user;
	@Inject
	private APIHelper apiHelper;

	public void setUser(@Observes LoggedEvent event) {
		this.user = apiHelper.getUser();
	}

	public void loadImage(@Observes LoggedEvent event) {
		switch ((ProtheusApiEnviroment) event.getProtheusEnviroment()) {
		case NSG:
			this.image = "NSG.png";
			this.currentLogoCss = nsgLogoCss;
			break;
		case SPG:
			this.image = "LOGOMARCA SPG.png";
			this.currentLogoCss = spgLogoCss;
			break;
		default:
			this.image = "Webp.net-resizeimage-gausslg.png";
			break;
		}
	}

	public String getImage() {
		return image;
	}

	public String getCurrentLogoCss() {
		return currentLogoCss;
	}

	public ProtheusUser getUser() {
		return user;
	}
}
