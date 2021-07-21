package com.portal.client.client.rest.providers.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(10)
public final class TokenHeaderSupport implements ClientRequestFilter {

	private final String token;

	private final String tokenPrefix;

	public TokenHeaderSupport(String token, String tokenPrefix) {
		super();
		this.token = token;
		this.tokenPrefix = tokenPrefix;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, tokenPrefix + " " + this.token);
	}
}
