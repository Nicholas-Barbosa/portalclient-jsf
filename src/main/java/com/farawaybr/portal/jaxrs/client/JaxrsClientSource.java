package com.farawaybr.portal.jaxrs.client;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Stateless(description = "Jaxrs Client source")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JaxrsClientSource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -41223482409279482L;

	private Client client;

	@PostConstruct
	public void init() {
		client = ClientBuilder.newBuilder().register(HttpStatusHandler.class).connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(50, TimeUnit.SECONDS).build();
	}

	@PreDestroy
	public void destroy() {
		client.close();
	}

	public Client getClient() {
		return client;
	}

}
