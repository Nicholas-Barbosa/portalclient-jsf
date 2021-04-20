package com.portal.client.rest;

import java.io.InputStream;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.portal.service.JacksonMapper;

public class SimpleRestClient implements RestClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient final JacksonMapper streamMapper;

	public SimpleRestClient() {
		this(null);

	}

	@Inject
	public SimpleRestClient(JacksonMapper streamMapper) {
		super();

		this.streamMapper = streamMapper;
	}

	@Override
	public <T> T getForEntity(String uri, Class<T> responseType, Object... params) {
		// TODO Auto-generated method stub
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
