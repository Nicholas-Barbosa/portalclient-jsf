package com.portal.client.jaxrs.client;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.portal.client.jaxrs.client.providers.filter.WebApplicationExceptionExceptionLauncherFilter;
import com.portal.client.jaxrs.client.providers.message.reader.JsonMessageReader;
import com.portal.client.jaxrs.client.providers.message.writer.JsonMessageWriter;

public interface RestClient {

	<T> T get(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			String media) throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	<T> Future<T> getAsync(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media) throws ExecutionException;

	<T> T get(String uri, String token, String tokenPrefix, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	<T> Future<T> getAsync(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) throws ExecutionException;

	<T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, String mediaType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	<T, E> T post(String uri, String token, String tokenPrefix, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	default Client getClientFollowingMediaType(String media) {
		Client client = media.equals("application/json")
				? ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
						.build().register(JsonMessageReader.class).register(JsonMessageWriter.class)
				: null;
		client.register(WebApplicationExceptionExceptionLauncherFilter.class);
		return client;
	}

	private void checkIfIsClientErrorException(ProcessingException p) {
		if (p.getCause() instanceof ClientErrorException)
			throw (ClientErrorException) p.getCause();
	}

	default void checkProcessingException(ProcessingException p)
			throws SocketTimeoutException, TimeoutException, SocketException {
		checkIfIsClientErrorException(p);
		Throwable rootException = p.getCause();
		if (rootException instanceof SocketException) {
			throw (SocketException) rootException;
		} else if (rootException instanceof ConnectException) {
			throw (ConnectException) rootException;
		} else if (rootException instanceof SocketTimeoutException) {
			throw (SocketTimeoutException) rootException;
		} else if (rootException instanceof InternalServerErrorException) {
			throw (InternalServerErrorException) rootException;
		}
	}
}