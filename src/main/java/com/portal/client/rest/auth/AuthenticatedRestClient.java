package com.portal.client.rest.auth;

import java.util.Map;

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
	<T, ERROR> Object getForEntity(String serviceApiKey, String endpoint, Class<T> responseType, Class<ERROR> errorType,
			Map<String, Object> queryParams, Map<String, Object> pathParams);

//	<T, R> T postForEntity(String path, R requestBodyType, MediaType mediaTypeRequestBody, Class<T> responseType,
//			Object... queryParams);

	/**
	 * Make the login(HTTP POST) against path .
	 * 
	 * 
	 * @param path
	 * @param queryParams
	 * @return
	 */
	<RQ, RP> RP login(String path, RQ requestBody, MediaType mediaRequestBody, Class<RP> responseType,
			Map<String,Object>queryParams);
}
