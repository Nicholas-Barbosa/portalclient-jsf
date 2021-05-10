package com.portal.client.rest.providers.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Priority;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
@Provider
@Priority(0)
public class ExceptionLauncherFilter implements ClientResponseFilter {

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		System.out.println("respinse status " + responseContext.getStatus());
		switch (responseContext.getStatus()) {
		case 404:
			throw new NotFoundException();
		case 500:
			throw new InternalServerErrorException();
		case 403:
			throw new ForbiddenException();
		case 401:
			readResponse(responseContext.getEntityStream());
			throw new NotAuthorizedException(Response.status(401).build());
		case 409:
			Response response = Response.status(409).entity(readResponse(responseContext.getEntityStream())).build();
			throw new ClientErrorException(response);
		}

	}

	private String readResponse(final InputStream input) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
