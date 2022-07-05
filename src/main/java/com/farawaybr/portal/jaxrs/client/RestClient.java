package com.farawaybr.portal.jaxrs.client;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public interface RestClient {

	<T> T get(String url, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			String media, Map<String, Object> headers);

	<T> Future<T> getAsync(String url, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media, Map<String, Object> headers);

	<T, E> T post(String url, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, String mediaType, Map<String, Object> headers);

	<T, E> CompletionStage<T> postAsynRX(String url, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType, Map<String, Object> headers);

	<T, E> T put(String url, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			E requestBody, String mediaType, Map<String, Object> headers);
}
