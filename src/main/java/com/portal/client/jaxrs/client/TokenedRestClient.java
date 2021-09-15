package com.portal.client.jaxrs.client;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface TokenedRestClient extends RestClient {

	<T> T get(String uri, String token, String tokenPrefix, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media);

	<T> Future<T> getAsync(String uri, String token, String tokenPrefix, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) throws ExecutionException;

	
	<T, E> T post(String uri, String token, String tokenPrefix, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType);

	<RESP, RQS> RESP put(String uri, String token, String tokenPrefix, Class<RESP> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, RQS requestBody, String mediaType);
}
