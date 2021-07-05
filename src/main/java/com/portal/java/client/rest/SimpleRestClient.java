package com.portal.java.client.rest;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import com.portal.java.cdi.qualifier.Simple;
import com.portal.java.exception.IllegalResponseStatusException;

@Simple
public class SimpleRestClient implements RestClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public <T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Client client = null;
		try {
			client = getClientFollowingMediaType(mediaType);
			WebTarget resource = client.target(uri);
			if (pathParams != null && !pathParams.isEmpty()) {
				resource = resource.resolveTemplatesFromEncoded(pathParams);
			}
			if (queryParams != null && !queryParams.isEmpty()) {
				for (String key : queryParams.keySet()) {
					resource = resource.queryParam(key, queryParams.get(key));
				}
			}
			Entity<E> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);

			return resource.request().accept(mediaType).post(entityRequest, responseType);

		} catch (ProcessingException p) {
			if (p.getCause() instanceof IllegalResponseStatusException) {
				System.out.println("illegal!");
				return this.post(uri, responseType, queryParams, pathParams, requestBody, mediaType);
			}
			RestClient.super.checkProcessingException(p);
			throw p;
		} finally {
			client.close();
		}

	}

}
