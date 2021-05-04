package com.portal.client.rest;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.portal.client.rest.providers.filter.ExceptionLauncherFilter;
import com.portal.client.rest.providers.message.reader.JsonObjectMessageReaderWriter;

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
	<T> T getForEntity(String uri, Class<T> responseType, Object... queryParams)
			throws SocketTimeoutException, TimeoutException, IllegalArgumentException, ConnectException;

	<T, E> T doPost(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, MediaType mediaType)
			throws SocketTimeoutException, TimeoutException, IllegalArgumentException, ConnectException;

	default Client getClientFollowingMediaType(MediaType media) {
		Client client = media == MediaType.APPLICATION_JSON_TYPE
				? ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
						.build().register(JsonObjectMessageReaderWriter.class)
				: null;
		client.register(ExceptionLauncherFilter.class);
		return client;
	}

	default void handleProcessingException(ProcessingException e)
			throws SocketTimeoutException, TimeoutException, IllegalArgumentException, ConnectException {
		if (e.getCause() instanceof SocketTimeoutException) {
			throw new SocketTimeoutException(e.getCause().getMessage());
		} else if (e.getCause() instanceof TimeoutException) {
			throw new TimeoutException(e.getCause().getMessage());
		} else if (e.getCause() instanceof IllegalArgumentException) {
			throw new IllegalArgumentException(e.getCause().getMessage());
		} else if (e.getCause() instanceof ConnectException) {
			throw new ConnectException(e.getCause().getMessage());
		} else if (e.getCause() instanceof NotFoundException)
			throw new NotFoundException();
		else
			e.printStackTrace();
	}
}
