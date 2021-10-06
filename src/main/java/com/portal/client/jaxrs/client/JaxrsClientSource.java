package com.portal.client.jaxrs.client;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.portal.client.jaxrs.client.providers.filter.HttpStatusExceptionLauncher;
import com.portal.client.jaxrs.client.providers.message.reader.JsonMessageReader;
import com.portal.client.jaxrs.client.providers.message.writer.JsonMessageWriter;

//@SessionScoped
@Stateless(description = "Jaxrs Client source")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JaxrsClientSource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -41223482409279482L;

	private final Client client;

	public JaxrsClientSource() {
		client = ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).register(JsonMessageReader.class)
				.register(JsonMessageWriter.class).register(HttpStatusExceptionLauncher.class)
				.build();
	}

	@PreDestroy
	public void destroy() {
		client.close();
	}

	public Client getClient() {
		return client;
	}

}
