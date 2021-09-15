package com.portal.client.jaxrs.client;

import java.util.function.Function;

import javax.ws.rs.client.Client;

public interface DeferredClientRequest {

	<T> T request(Function<Client, T> lambda);
}
