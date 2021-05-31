package com.portal.jaxrs.resource;

import java.time.LocalDate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.portal.dto.UserDTO;

@Path("/user")
public class UserResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response show() {
		return Response.ok(new UserDTO("Nicholas", LocalDate.now())).build();
	}
}
