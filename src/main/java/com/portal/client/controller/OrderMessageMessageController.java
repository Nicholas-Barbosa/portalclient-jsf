package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.portal.client.util.jsf.FacesUtils;

@RequestScoped
@Named
public class OrderMessageMessageController {

	public void displayMessage() {
		FacesUtils.info(null, "Mensagem salva!", null, "growl");
	}
}
