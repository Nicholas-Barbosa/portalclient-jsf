package com.portal.jaxrs.controller;

import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("/login")
public class LoginControllerJax {

	@GET
	public String loginPage() {
		return "login.xhtml";
	}
}
