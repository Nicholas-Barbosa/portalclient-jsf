package com.portal.client.jaxrs.client;

import java.util.concurrent.Future;
import java.util.function.Function;

import javax.ws.rs.client.Client;

public interface DeferredClientRequest {

	<T> T request(Function<Client, T> lambda);

	<T> Future<T> requestAsync(Function<Client, Future<T>> lambda);
}
