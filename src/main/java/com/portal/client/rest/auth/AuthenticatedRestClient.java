package com.portal.client.rest.auth;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

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
	<T, ERROR> T getForEntity(String serviceApiKey, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, MediaType media)
			throws ProcessingException, SocketTimeoutException, ConnectException, IllegalArgumentException,
			TimeoutException;

}
