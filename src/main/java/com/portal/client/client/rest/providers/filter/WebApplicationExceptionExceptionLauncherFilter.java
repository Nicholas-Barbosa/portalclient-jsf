package com.portal.client.client.rest.providers.filter;

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

import com.portal.client.exception.IllegalResponseStatusException;

@Provider
@Priority(0)
public class WebApplicationExceptionExceptionLauncherFilter implements ClientResponseFilter {

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		switch (responseContext.getStatus()) {
		case 404:
			String responseTxt = readResponse(responseContext.getEntityStream());
			Response responseNotFound = Response.status(404).entity(responseTxt).build();
			throw new NotFoundException(responseNotFound);
		case 500:
			throw new InternalServerErrorException();
		case 403:
			throw new ForbiddenException();
		case 401:
			System.out.println("401!!");
			requestContext.getStringHeaders().forEach((k, v) -> {
				System.out.println(k + ":" + v);
			});
			System.out.println(readResponse(responseContext.getEntityStream()));
			throw new NotAuthorizedException(Response.status(401).build());
		case 409:
			Response response = Response.status(409).entity(readResponse(responseContext.getEntityStream())).build();
			throw new ClientErrorException(response);
		case -1:
			throw new IllegalResponseStatusException();

		}

	}

	private String readResponse(final InputStream input) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			StringBuilder response = new StringBuilder();
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null)
				response.append(currentLine);
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
