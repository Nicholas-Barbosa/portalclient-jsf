package com.portal.client.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import com.portal.client.security.api.ProtheusCompanyApiEnv;
import com.portal.client.security.auth.AuthenticateddEvent;

@SessionScoped
@Named
public class TopBarPreferences implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5243438532867147574L;
	private static final String nsgLogoCss = "width:5.5vw;height:9.5vh;border-radius:50%";
	private String image;
	private String currentLogoCss;

	public void loadImage(@Observes AuthenticateddEvent event) {
		switch ((ProtheusCompanyApiEnv) event.getProtheusEnviroment()) {
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

	public String getImage() {
		return image;
	}

	public String getCurrentLogoCss() {
		return currentLogoCss;
	}
}