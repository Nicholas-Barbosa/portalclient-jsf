package com.farawaybr.portal.jaxrs.client;

import java.util.Map;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.jaxrs.client.JaxrsRequestData.JaxrsRequestDataBuilder;

@ApplicationScoped
public class RestClientImpl implements RestClient {

	@Inject
	private JaxrsClientRequester requester;

	@Override
	public <T> T get(String url, Class<T> responseType, Map<String, Object> queryParams, Map<String, Object> pathParams,
			String mediaType, Map<String, Object> headers) {
		// TODO Auto-generated method stub
		return requester.request(JaxrsRequestDataBuilder.getInstance().headers(headers).queryParams(queryParams)
				.pathParams(pathParams).url(url).method("GET").mediaType(mediaType).build(), responseType);
	}

	@Override
	public <T, E> T post(String url, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType, Map<String, Object> headers) {
		return requester.request(
				JaxrsRequestDataBuilder.getInstance().url(url).mediaType(mediaType).pathParams(pathParams)
						.queryParams(queryParams).requestBody(requestBody).headers(headers).method("POST").build(),
				responseType);
	}

	@Override
	public <T, E> T put(String url, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, String mediaType, Map<String, Object> headers) {
		// TODO Auto-generated method stub
		return requester.request(
				JaxrsRequestDataBuilder.getInstance().url(url).mediaType(mediaType).pathParams(pathParams)
						.queryParams(queryParams).requestBody(requestBody).headers(headers).method("PUT").build(),
				responseType);
	}

	@Override
	public <T> Future<T> getAsync(String url, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String mediaType, Map<String, Object> headers) {
		// TODO Auto-generated method stub
		return requester.asyncRequest(JaxrsRequestDataBuilder.getInstance().headers(headers).queryParams(queryParams)
				.pathParams(pathParams).url(url).method("GET").mediaType(mediaType).build(), responseType);
	}

}
