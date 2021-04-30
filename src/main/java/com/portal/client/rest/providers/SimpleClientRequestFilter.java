package com.portal.client.rest.providers;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class SimpleClientRequestFilter implements ClientRequestFilter {

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		System.out.println("filter!");
		requestContext.getHeaders().forEach((k, v) -> {
			System.out.println(k + ":" + " " + v);
		});

	}

}
