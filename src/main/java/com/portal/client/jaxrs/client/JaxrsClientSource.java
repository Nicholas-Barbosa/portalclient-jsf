package com.portal.client.jaxrs.client;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.ClientBuilder;

import com.portal.client.jaxrs.client.providers.message.reader.JsonMessageReader;
import com.portal.client.jaxrs.client.providers.message.writer.JsonMessageWriter;

@SessionScoped
public class JaxrsClientSource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -41223482409279482L;

	private int initialPoolSize = 5;

	private int maxPoolSize = 10;

	private List<ClientWrapper> clients;

	@PostConstruct
	public void init() {
		clients = Stream.iterate(this.createClient(), t -> this.createClient()).limit(initialPoolSize)
				.collect(Collectors.toList());
	}

	@PreDestroy
	public void destroy() {
		clients.forEach(c -> {
			c.getClient().close();
			c = null;
		});
		clients = null;
	}

	private ClientWrapper createClient() {
		return new ClientWrapper(false, ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).build()
				.register(JsonMessageReader.class).register(JsonMessageWriter.class));
	}

	private ClientWrapper getClientFromPool() {
		return clients.parallelStream().filter(c -> !c.isInUse()).findAny().orElseGet(() -> {
			if (clients.size() <= maxPoolSize) {
				ClientWrapper wrapper = createClient();
				wrapper.setInUse(true);
				return wrapper;
			}
			return this.getClientFromPool();
		});
	}

	public ClientWrapper getClient() {
		return getClientFromPool();
	}

}
