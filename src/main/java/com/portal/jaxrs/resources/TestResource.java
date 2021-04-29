package com.portal.jaxrs.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("teste")
public class TestResource {
	@GET
	public Response teste(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
		return Response.ok(page + " " + pageSize).build();
	}
}
