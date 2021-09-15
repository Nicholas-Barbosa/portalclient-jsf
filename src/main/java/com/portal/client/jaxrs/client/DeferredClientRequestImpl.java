package com.portal.client.jaxrs.client;

import java.io.Serializable;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;

@ApplicationScoped
public class DeferredClientRequestImpl implements Serializable, DeferredClientRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8420422881382830155L;

	@Inject
	private JaxrsClientSource clientDS;

	@Override
	public <T> T request(Function<Client, T> lambda) {
		return lambda.apply(clientDS.getClient());
	}

}
