package com.portal.client.rest;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.portal.client.rest.providers.filter.ProcessingExceptionLauncherFilter;
import com.portal.client.rest.providers.message.reader.JsonMessageReader;
import com.portal.client.rest.providers.message.writer.JsonMessageWriter;

public interface RestClient extends Serializable {

	<T> T get(String uri, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media) throws ProcessingException;

	<T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, String mediaType);

	default Client getClientFollowingMediaType(String media) {
		Client client = media.equals("application/json")
				? ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
						.build().register(JsonMessageReader.class).register(JsonMessageWriter.class)
				: null;
		client.register(ProcessingExceptionLauncherFilter.class);
		return client;
	}

	default void checkIfClientErrorException(ProcessingException p) {
		if (p.getCause() instanceof ClientErrorException)
			throw (ClientErrorException) p.getCause();
	}

	default void checkProcessingException(ProcessingException p) {

	}
}
