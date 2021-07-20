package com.portal.client.client.rest;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.portal.client.client.rest.providers.filter.ProcessingExceptionLauncherFilter;
import com.portal.client.client.rest.providers.message.reader.JsonMessageReader;
import com.portal.client.client.rest.providers.message.writer.JsonMessageWriter;
import com.portal.client.exception.IllegalResponseStatusException;

public interface RestClient extends Serializable {

	default <T> T get(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Client client = null;
		try {
			client = getClientFollowingMediaType(media);
			WebTarget resource = client.target(uri);
			if (pathParams != null) {
				resource = resource.resolveTemplatesFromEncoded(pathParams);
			}
			if (queryParams != null) {
				Set<String> paramsInSet = queryParams.keySet();
				for (String st : paramsInSet) {
					try {
						resource = resource.queryParam(st, URLEncoder.encode(queryParams.get(st).toString(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			Response rawResponse = resource.request().accept(media).get();
			T t = rawResponse.readEntity(responseType);
			return t;
		} catch (ProcessingException e) {
			if (e.getCause() instanceof IllegalResponseStatusException) {
				return this.get(uri, responseType, queryParams, pathParams, media);
			}
			checkProcessingException(e);
			throw e;
		} finally {
			if (client != null)
				client.close();
		}

	}

	<T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, String mediaType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	default Client getClientFollowingMediaType(String media) {
		Client client = media.equals("application/json")
				? ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
						.build().register(JsonMessageReader.class).register(JsonMessageWriter.class)
				: null;
		client.register(ProcessingExceptionLauncherFilter.class);
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
