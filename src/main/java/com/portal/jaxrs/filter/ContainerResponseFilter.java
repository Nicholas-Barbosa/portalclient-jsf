package com.portal.jaxrs.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@PreMatching
@Provider
public class ContainerResponseFilter implements ContainerRequestFilter, javax.ws.rs.container.ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
	}

}
