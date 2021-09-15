package com.portal.client.jaxrs.client;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.portal.client.exception.IllegalResponseStatusException;
import com.portal.client.jaxrs.client.aop.IllegalResponsePointCutJoinPoint;
import com.portal.client.jaxrs.client.providers.filter.TokenHeaderSupport;

@ApplicationScoped
public class TokenedRestClientImpl implements TokenedRestClient {

	@Inject
	private DeferredClientRequest clientRequest;

	public <T> T get(String uri, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			String media) {
		return this.get(uri, null, null, responseType, queryParams, pathParams, media);

	}

	@IllegalResponsePointCutJoinPoint
	public <T> T get(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) {
		return clientRequest.request(c -> {
			WebTarget resource = getWebTarget(c, uri, queryParams, pathParams)
					.register(new TokenHeaderSupport(token, tokenPrefix));
			Response rawResponse = resource.request().accept(media).get();
			T t = rawResponse.readEntity(responseType);
			return t;
		});
//		Client client = null;
//		try {
//			client = getClient(token, tokenPrefix, media);
//
//			WebTarget resource = getWebTarget(client, uri, queryParams, pathParams);
//			Response rawResponse = resource.request().accept(media).get();
//			T t = rawResponse.readEntity(responseType);
//			return t;
//		} catch (ProcessingException e) {
//			if (e.getCause() instanceof IllegalResponseStatusException) {
//				return this.get(uri, token, tokenPrefix, responseType, queryParams, pathParams, media);
//			}
//			throw e;
//		} finally {
//			if (client != null)
//				client.close();
//		}

	}

	public <T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType) {
		return this.post(uri, null, null, responseType, queryParams, pathParams, requestBody, mediaType);

	}

	public <T, E> T post(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, E requestBody, String mediaType) {
		Client client = null;
		try {
			client = getClient(token, tokenPrefix, mediaType);

			WebTarget resource = getWebTarget(client, uri, queryParams, pathParams);

			Entity<E> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);

			return resource.request().accept(mediaType).post(entityRequest, responseType);

		} catch (ProcessingException p) {
			if (p.getCause() instanceof IllegalResponseStatusException) {
				return this.post(uri, token, tokenPrefix, responseType, queryParams, pathParams, requestBody,
						mediaType);
			}
			throw p;
		} finally {
			client.close();
		}

	}

	@Override
	public <T> Future<T> getAsync(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media) throws ExecutionException {
		return this.getAsync(uri, null, null, responseType, queryParams, pathParams, media);
	}

	@Override
	public <T> Future<T> getAsync(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) throws ExecutionException {
		Client client = null;
		client = getClient(token, tokenPrefix, media);

		WebTarget resource = getWebTarget(client, uri, queryParams, pathParams);

		return resource.request().accept(media).async().get(responseType);
	}

	private Client getClient(String token, String tokenPrefix, String mediaType) {
		Client client = null;
		client = getClientFollowingMediaType(mediaType);
		if (token != null && tokenPrefix != null) {
			TokenHeaderSupport tokenHeaderSupport = new TokenHeaderSupport(token, tokenPrefix);
			client = client.register(tokenHeaderSupport);
		}
		return client;
	}

	private WebTarget getWebTarget(Client client, String url, Map<String, Object> queryParams,
			Map<String, Object> pathParams) {
		WebTarget resource = client.target(url);
		if (pathParams != null && !pathParams.isEmpty()) {
			resource = resource.resolveTemplatesFromEncoded(pathParams);
		}
		if (queryParams != null && !queryParams.isEmpty()) {
			for (String key : queryParams.keySet()) {
				resource = resource.queryParam(key, queryParams.get(key));
			}
		}
		return resource;
	}

	@Override
	public <RESP, RQS> RESP put(String uri, String token, String tokenPrefix, Class<RESP> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, RQS requestBody, String mediaType) {
		Client client = null;
		try {
			client = getClient(token, tokenPrefix, mediaType);
			WebTarget resource = getWebTarget(client, uri, queryParams, pathParams);
			Entity<RQS> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);

			return resource.request().accept(mediaType).put(entityRequest, responseType);
		} finally {
			client.close();
		}
	}
}
