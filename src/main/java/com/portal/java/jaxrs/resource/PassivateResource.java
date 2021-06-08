package com.portal.java.jaxrs.resource;

import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.portal.java.ejb.PassivationBean;

@Path("/passivate")
public class PassivateResource {

	@GET
	public Response passivate() {
		int i = 1;
		try {
			InitialContext in = new InitialContext();
			while (++i <= 15) {
				PassivationBean pass = (PassivationBean) in.lookup("java:global/PortalAppClient/PassivationBean");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/resize")
	public Response cacheResize() {
		try {
			InitialContext in = new InitialContext();
			PassivationBean pass = (PassivationBean) in.lookup("java:global/PortalAppClient/PassivationBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

}
