package com.portal.client.rest.auth;

import java.text.MessageFormat;

import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import com.portal.security.api.ServiceApi;

@SessionScoped
public class OAuth2AuthenticatedRestClient implements AuthenticatedRestClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2516211534967007393L;

	@Override
	public <T> T getForEntity(String uri, Class<T> responseType, Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, R> T postFoEntity(String uri, R requestBody, MediaType mediaRequestBody, Class<T> responseType,
			Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultServiceApi(ServiceApi service) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getForEntity(String path, Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, R> T postForEntity(String path, R requestBodyType, MediaType mediaTypeRequestBody, Class<T> responseType,
			Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <RQ, RP> RP login(String path, RQ requestBody, MediaType mediaRequestBody, Class<RP> responseType,
			Object... queryParams) {
		// TODO Auto-generated method stub

		return client.target(MessageFormat.format(path, queryParams)).request()
				.post(requestBody != null ? Entity.entity(requestBody, mediaRequestBody) : null, responseType);
	}

}
