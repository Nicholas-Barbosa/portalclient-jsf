package com.portal.client.client.rest.providers.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(10)
public final class OAuth2Support implements ClientRequestFilter {

	private final String token;

	public OAuth2Support(String token) {
		super();
		this.token = token;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + this.token);
	}
}
