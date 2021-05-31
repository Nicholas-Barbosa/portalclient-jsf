package com.portal.client.rest;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.portal.client.rest.providers.filter.ExceptionLauncherFilter;
import com.portal.client.rest.providers.message.reader.JsonMessageReader;
import com.portal.client.rest.providers.message.writer.JsonMessageWriter;

public interface RestClient extends Serializable {

	/**
	 * Execute HTTP get to specific uri.Return an object of type T, that will be
	 * deserialized after the response is ready.
	 * 
	 * @param <T>
	 * @param uri
	 * @param mediaType    for accept header
	 * @param classGeneric
	 * @return
	 */
	<T> T getForEntity(String uri, Class<T> responseType, Object... queryParams) throws SocketTimeoutException,
			TimeoutException, IllegalArgumentException, ConnectException, SocketException;

	<T, E> T doPost(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, String mediaType);

	default Client getClientFollowingMediaType(String media) {
		Client client = media.equals("application/json")
				? ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
						.build().register(JsonMessageReader.class).register(JsonMessageWriter.class)
				: null;
		client.register(ExceptionLauncherFilter.class);
		return client;
	}

	default void checkIfClientErrorException(ProcessingException p) {
		if (p.getCause() instanceof ClientErrorException)
			throw (ClientErrorException) p.getCause();
	}
}
