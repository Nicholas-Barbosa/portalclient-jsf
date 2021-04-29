package com.portal.client.rest.providers.message.reader;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class ContianerResponseImpl implements ClientRequestFilter {


	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
	requestContext.getHeaders().forEach((k,v)->{
		System.out.println(k+" "+ v);
	});
		
	}

}
