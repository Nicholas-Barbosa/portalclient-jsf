package com.portal.client.jaxrs.client;

import java.util.concurrent.Future;
import java.util.function.BiFunction;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public interface DeferredClientRequest {

	<T> T request(BiFunction<Client, WebTarget, T> lambda, WebTargetData wb);

	<T> Future<T> requestAsync(BiFunction<Client, WebTarget, Future<T>> lambda, WebTargetData wb);
}
