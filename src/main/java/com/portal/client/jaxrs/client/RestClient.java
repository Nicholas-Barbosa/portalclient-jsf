package com.portal.client.jaxrs.client;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.portal.client.jaxrs.client.providers.filter.WebApplicationExceptionExceptionLauncherFilter;
import com.portal.client.jaxrs.client.providers.message.reader.JsonMessageReader;
import com.portal.client.jaxrs.client.providers.message.writer.JsonMessageWriter;

public interface RestClient {

	<T> T get(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			String media);

	<T> Future<T> getAsync(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media) throws ExecutionException;

	<T> T get(String uri, String token, String tokenPrefix, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media);

	<T> Future<T> getAsync(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) throws ExecutionException;

	<T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, String mediaType);

	<T, E> T post(String uri, String token, String tokenPrefix, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType);

	default Client getClientFollowingMediaType(String media) {
		Client client = media.equals("application/json")
				? ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
						.build().register(JsonMessageReader.class).register(JsonMessageWriter.class)
				: null;
		client.register(WebApplicationExceptionExceptionLauncherFilter.class);
		return client;
	}

}
