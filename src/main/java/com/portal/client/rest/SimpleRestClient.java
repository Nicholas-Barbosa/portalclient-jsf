package com.portal.client.rest;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.portal.service.JacksonHoldByteStream;

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
	public <T, R> T postFoEntity(String uri, R requestBody, MediaType mediaRequestBody, Class<T> responseType,
			Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

}
