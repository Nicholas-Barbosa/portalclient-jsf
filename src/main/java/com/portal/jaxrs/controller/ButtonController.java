package com.portal.jaxrs.controller;

import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("/button")
public class ButtonController {

	@GET
	public String returnButtonPage() {
		return "buton.xhtml";
	}
}
