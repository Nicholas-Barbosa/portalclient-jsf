package com.portal.client.rest.auth;

import java.util.Map;
import java.util.concurrent.Future;

import javax.ws.rs.ProcessingException;

import com.portal.client.rest.RestClient;

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

	<T> Future<T> getAsync(String serviceApiKey, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media) throws ProcessingException;

	<T, U> T post(String serviceApiKey, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media, U requestBody) throws ProcessingException;

	@Override
	default <T, E> T post(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

}
