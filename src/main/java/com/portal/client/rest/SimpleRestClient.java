package com.portal.client.rest;

import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.portal.cdi.qualifier.Simple;
import com.portal.service.JacksonHoldByteStream;

@Simple
public class SimpleRestClient implements RestClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient final JacksonHoldByteStream streamMapper;

	public SimpleRestClient() {
		this(null);

	}

	@Inject
	public SimpleRestClient(JacksonHoldByteStream streamMapper) {
		super();
		this.streamMapper = streamMapper;
	}

	@Override
	public <T> T getForEntity(String uri, Class<T> responseType, Object... params) {
		// TODO Auto-generated method stub
		Client client = ClientBuilder.newBuilder().readTimeout(10, TimeUnit.SECONDS).build();

		Response response = client.target(MessageFormat.format(uri, params)).request().get();
		T responseTyped = this.streamMapper.readValue((InputStream) response.getEntity(), responseType);

		return responseTyped;
	}

	@Override
	public <T, E> T doPost(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, MediaType mediaType)
			throws SocketTimeoutException, IllegalArgumentException, TimeoutException, SocketException {
		Client client = null;
		try {
			client = getClientFollowingMediaType(mediaType);
			WebTarget resource = client.target(uri);
			if (pathParams != null && !pathParams.isEmpty()) {
				resource = resource.resolveTemplatesFromEncoded(pathParams);
			}
			if (queryParams != null && !queryParams.isEmpty()) {
				for (String key : queryParams.keySet()) {
					resource = resource.queryParam(key, queryParams.get(key));
				}
			}
			Entity<E> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType) : null;

			return resource.request().post(entityRequest, responseType);
		} catch (ProcessingException e) {
			RestClient.super.handleProcessingException(e);
		} finally {
			client.close();
		}

		return null;
	}

}
