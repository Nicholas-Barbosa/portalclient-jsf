package com.portal.client.rest.auth;

import javax.ws.rs.core.MediaType;

import com.portal.client.rest.RestClient;
import com.portal.security.api.ServiceApi;

public interface AuthenticatedRestClient extends RestClient {

	void setDefaultServiceApi(ServiceApi service);

	<T> T getForEntity(String path, Object... queryParams);

	<T, R> T postForEntity(String path, R requestBodyType, MediaType mediaTypeRequestBody, Class<T> responseType,
			Object... queryParams);

	/**
	 * Make the login(HTTP POST) against path and set the returned service to a
	 * object of ServiceApi. Return true if status 201.
	 * 
	 * 
	 * @param path
	 * @param queryParams
	 * @return
	 */
	<RQ, RP> RP login(String path, RQ requestBody, MediaType mediaRequestBody, Class<RP> responseType,
			Object... queryParams);
}
