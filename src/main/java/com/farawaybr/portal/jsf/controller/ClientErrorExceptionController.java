package com.farawaybr.portal.jsf.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.core.Response;

@RequestScoped
@Named
public class ClientErrorExceptionController {

	private Response response;
	
	@PostConstruct
	public void initResponse() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		response = (Response) sessionMap.get("response");
		sessionMap.remove("response");
	}

	public Response getResponse() {
		return response;
	}
}
