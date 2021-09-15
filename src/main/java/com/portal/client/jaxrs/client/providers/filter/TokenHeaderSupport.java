package com.portal.client.jaxrs.client.providers.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(1)
public final class TokenHeaderSupport implements ClientRequestFilter {

	private final String token;

	private final String tokenPrefix;

	private final Logger logger = LoggerFactory.getLogger(TokenHeaderSupport.class);

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
