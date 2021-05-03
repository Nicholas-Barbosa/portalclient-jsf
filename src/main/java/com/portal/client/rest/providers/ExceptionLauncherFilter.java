package com.portal.client.rest.providers;

import java.io.IOException;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

public class ExceptionLauncherFilter implements ClientResponseFilter {

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		switch (responseContext.getStatus()) {
		case 404:
			throw new NotFoundException();
		case 500:
			throw new InternalServerErrorException();
		case 403:
			throw new ForbiddenException();
		}

	}

}
