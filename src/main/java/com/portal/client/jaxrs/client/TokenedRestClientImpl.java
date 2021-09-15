package com.portal.client.jaxrs.client;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.portal.client.jaxrs.client.providers.filter.TokenHeaderSupport;

@ApplicationScoped
public class TokenedRestClientImpl implements TokenedRestClient {

	@Inject
	private DeferredClientRequest clientRequest;

	public <T> T get(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			String media) {
		return this.get(uri, null, null, responseType, queryParams, pathParams, media);

	}

	public <T> T get(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) {
		return clientRequest.request(c -> {
			WebTarget resource = getWebTarget(c, uri, queryParams, pathParams, token, tokenPrefix);
			Response rawResponse = resource.request().accept(media).get();
			T t = rawResponse.readEntity(responseType);
			return t;
		});

	}

	public <T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType) {
		return this.post(uri, null, null, responseType, queryParams, pathParams, requestBody, mediaType);

	}

	public <T, E> T post(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, E requestBody, String mediaType) {
		return clientRequest.request(c -> {
			WebTarget resource = getWebTarget(c, uri, queryParams, pathParams, token, tokenPrefix);

			Entity<E> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);

			return resource.request().accept(mediaType).post(entityRequest, responseType);
		});

	}

	@Override
	public <T> Future<T> getAsync(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media) throws ExecutionException {
		return this.getAsync(uri, null, null, responseType, queryParams, pathParams, media);
	}

	@Override
	public <T> Future<T> getAsync(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) throws ExecutionException {
		return clientRequest.requestAsync(c -> {
			WebTarget resource = getWebTarget(c, uri, queryParams, pathParams, token, tokenPrefix);

			return resource.request().accept(media).async().get(responseType);
		});

	}

	private WebTarget getWebTarget(Client client, String url, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String token, String tokenPrefix) {
		WebTarget resource = client.target(url);
		if (pathParams != null && !pathParams.isEmpty()) {
			resource = resource.resolveTemplatesFromEncoded(pathParams);
		}
		if (queryParams != null && !queryParams.isEmpty()) {
			for (String key : queryParams.keySet()) {
				resource = resource.queryParam(key, queryParams.get(key));
			}
		}
		return resource.register(new TokenHeaderSupport(token, tokenPrefix));
	}

	@Override
	public <RESP, RQS> RESP put(String uri, String token, String tokenPrefix, Class<RESP> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, RQS requestBody, String mediaType) {
		return clientRequest.request(c -> {
			WebTarget resource = getWebTarget(c, uri, queryParams, pathParams, token, tokenPrefix);
			Entity<RQS> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);
			return resource.request().accept(mediaType).put(entityRequest, responseType);
		});

	}
}
