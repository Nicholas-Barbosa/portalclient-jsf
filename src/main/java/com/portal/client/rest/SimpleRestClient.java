package com.portal.client.rest;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import com.portal.cdi.qualifier.Simple;

@Simple
public class SimpleRestClient implements RestClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public <T> T get(String uri, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType)
			throws SocketTimeoutException, ConnectException, TimeoutException {
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
			RestClient.super.checkProcessingException(p);
			throw p;
		} finally {
			client.close();
		}

	}

}
