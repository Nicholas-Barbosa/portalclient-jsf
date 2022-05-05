package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.farawaybr.portal.util.jsf.FacesUtils;

@RequestScoped
@Named
public class OrderMessageMessageController {

	public void displayMessage() {
		FacesUtils.info(null, "Mensagem salva!", null, "growl");
	}
}
