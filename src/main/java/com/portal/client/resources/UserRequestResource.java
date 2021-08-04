package com.portal.client.resources;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.portal.client.dto.NoRequests;
import com.portal.client.dto.RequestJson;
import com.portal.client.service.route.RequestTracker;

@Path("userRequest")
public class UserRequestResource {

	private RequestTracker requestTracker;
	private HttpSession session;

	@Inject
	public UserRequestResource(RequestTracker requestTracker, HttpSession session) {
		super();
		this.requestTracker = requestTracker;
		this.session = session;
	}

	@GET
	@Produces("application/json")
	public Response findAll() {

		if (requestTracker.isEmpty())
			return Response.status(404)
					.entity(new NoRequests("No requests until now for session ID: " + session.getId())).build();
		return Response.status(200).entity(requestTracker.getAll().parallelStream().map(RequestJson::new)
				.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll)).build();
	}
}
