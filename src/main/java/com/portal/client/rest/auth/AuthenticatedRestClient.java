package com.portal.client.rest.auth;

import java.util.Map;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

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
	<T> T getForEntity(String serviceApiKey, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, MediaType media) throws ProcessingException;

	<T, U> T post(String serviceApiKey, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, MediaType media, U requestBody) throws ProcessingException;

	@Override
	default <T, E> T doPost(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, MediaType mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default <T> T getForEntity(String uri, Class<T> responseType, Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

}
