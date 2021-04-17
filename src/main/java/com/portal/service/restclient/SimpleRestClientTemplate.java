package com.portal.service.restclient;

import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.portal.service.JacksonMapper;

public class SimpleRestClientTemplate implements RestClientTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Client client = ClientBuilder.newClient();

	private final JacksonMapper streamMapper;

	public SimpleRestClientTemplate() {
		this(null);

	}

	@Inject
	public SimpleRestClientTemplate(JacksonMapper streamMapper) {
		super();

		this.streamMapper = streamMapper;
	}

	@Override
	public <T> T getForEntity(String uri, Class<T> classGeneric, MultivaluedMap<String, Object> headers) {
		Response response = client.target(uri).request().headers(headers).get();

		T tResponse = this.streamMapper.readValue((InputStream) response.getEntity(), classGeneric);

		return tResponse;
	}

	@Override
	public <T> T getForEntity(String uri, Class<T> classGeneric) {
		return client.target(uri).request().get(classGeneric);
	}

	@Override
	public <T> T postForEntity(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getForEntity(String uri, Class<T> classGeneric, RestClientHeader restClientHeader) {
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.add(restClientHeader.getKey(), restClientHeader.getValue());
		return this.getForEntity(uri, classGeneric, headers);
	}

}
