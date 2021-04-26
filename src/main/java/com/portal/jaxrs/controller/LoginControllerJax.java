package com.portal.jaxrs.controller;

import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.krazo.engine.Viewable;

@Controller
@Path("/login")
public class LoginControllerJax {

	@GET
	public Viewable loginPage() {
		return new Viewable("login.xhtml");
	}
}
