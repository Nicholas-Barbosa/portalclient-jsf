package com.portal.client.rest;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.Simple;

@Simple
public class SimpleRestClient implements RestClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public <T> T getForEntity(String uri, Class<T> responseType, Object... params) {

		return null;
	}

	@Override
	public <T, E> T doPost(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, MediaType mediaType) {
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

		} finally {
			client.close();
		}

	}

}
