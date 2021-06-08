package com.portal.java.client.rest.auth;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.portal.java.client.rest.RestClient;

public interface AuthenticatedRestClient extends RestClient {

	/**
	 * HTTP get to ServiceApi base path resolved with endpoint. Return an object
	 * reference type referencing to error type if status different of 200,201 is
	 * returned.Else will return an object reference to response type.
	 * 
	 * @param <T>
	 * @param endpoint
	 * @param queryParams
	 * @return
	 */

	<T> T get(String resourceKey, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException;
	// TODO Auto-generated method stub

	<T> Future<T> getAsync(String serviceApiKey, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException;

	<T, U> T post(String serviceApiKey, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media, U requestBody)
			throws SocketTimeoutException, ConnectException, TimeoutException;

	@Override
	default <T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

}
