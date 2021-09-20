package com.portal.client.jaxrs.client;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

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
		return clientRequest.request((c, resource) -> {
			Response rawResponse = resource.request().accept(media).get();
			T t = rawResponse.readEntity(responseType);
			return t;
		}, WebTargetDataBuilder.getInstance().withPathParams(pathParams).withQueryParams(queryParams).withToken(token)
				.withPrefixToken(tokenPrefix).withUri(uri).build());

	}

	public <T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType) {
		return this.post(uri, null, null, responseType, queryParams, pathParams, requestBody, mediaType);

	}

	public <T, E> T post(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, E requestBody, String mediaType) {
		return clientRequest.request((c, resource) -> {

			Entity<E> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);

			return resource.request().accept(mediaType).post(entityRequest, responseType);
		}, WebTargetDataBuilder.getInstance().withPathParams(pathParams).withQueryParams(queryParams).withToken(token)
				.withPrefixToken(tokenPrefix).withUri(uri).build());

	}

	@Override
	public <T> Future<T> getAsync(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media) throws ExecutionException {
		return this.getAsync(uri, null, null, responseType, queryParams, pathParams, media);
	}

	@Override
	public <T> Future<T> getAsync(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) throws ExecutionException {
		return clientRequest.requestAsync((c, resource) -> {
			return resource.request().accept(media).async().get(responseType);
		}, WebTargetDataBuilder.getInstance().withPathParams(pathParams).withQueryParams(queryParams).withToken(token)
				.withPrefixToken(tokenPrefix).withUri(uri).build());

	}

	@Override
	public <RESP, RQS> RESP put(String uri, String token, String tokenPrefix, Class<RESP> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, RQS requestBody, String mediaType) {
		return clientRequest.request((c, resource) -> {
			Entity<RQS> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);
			return resource.request().accept(mediaType).put(entityRequest, responseType);
		}, WebTargetDataBuilder.getInstance().withPathParams(pathParams).withQueryParams(queryParams).withToken(token)
				.withPrefixToken(tokenPrefix).withUri(uri).build());

	}
}
