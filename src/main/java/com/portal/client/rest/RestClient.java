package com.portal.client.rest;

import java.io.Serializable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public interface RestClient extends Serializable {

	Client client = ClientBuilder.newClient();

	/**
	 * Execute HTTP get to specific uri.Return an object of type T, that will be
	 * deserialized after the response is ready.
	 * 
	 * @param <T>
	 * @param uri
	 * @param mediaType    for accept header
	 * @param classGeneric
	 * @return
	 */
	<T> T getForEntity(String uri, Class<T> responseType, Object... queryParams);

	<T, R> T postFoEntity(String uri, R requestBody, MediaType mediaRequestBody, Class<T> responseType,
			Object... queryParams);
}
