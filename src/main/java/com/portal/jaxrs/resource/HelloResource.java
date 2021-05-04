package com.portal.jaxrs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hello")
public class HelloResource {

	@GET
	public String sayHello() {
		return "hello";
	}
}
