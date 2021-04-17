package com.portal.service.restclient;

import java.io.Serializable;

import javax.ws.rs.core.MultivaluedMap;

public interface RestClientTemplate extends Serializable {

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
	<T> T getForEntity(String uri, Class<T> classGeneric);

	<T> T getForEntity(String uri, Class<T> classGeneric, MultivaluedMap<String, Object> headers);

	<T> T getForEntity(String uri, Class<T> classGeneric, RestClientHeader restClientHeader);

	<T> T postForEntity(String uri);
}
