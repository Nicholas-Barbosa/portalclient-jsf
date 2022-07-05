package com.farawaybr.portal.jaxrs.client;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.farawaybr.portal.cdi.aop.annotations.IllegalResponsePointCutJoinPoint;

@ApplicationScoped
public class JaxrsClientRequester {

	@Inject
	private JaxrsClientSource clientSource;

	private final InvocationCallback<Response> asyncCallback = new InvocationCallback<Response>() {

		@Override
		public void completed(Response response) {
			// TODO Auto-generated method stub

		}

		@Override
		public void failed(Throwable throwable) {

		}

	};

	@IllegalResponsePointCutJoinPoint
	public <T> T request(JaxrsRequestData data, Class<T> responseType) {
		WebTarget target = getTarget(data.getUrl(), data.getQueryParams(), data.getPathParams());

		Builder request = target.request(data.getMediaType());
		resolveHeaders(data.getHeaders(), request);
		final Response response = request.method(data.getMethod(),
				Entity.entity(data.getRequestBody(), data.getMediaType()));
		try (response) {
			return response.readEntity(responseType);
		}

	}

	public <T> Future<T> asyncRequest(JaxrsRequestData data, Class<T> responseType) {
		WebTarget target = getTarget(data.getUrl(), data.getQueryParams(), data.getPathParams());

		Builder request = target.request(data.getMediaType());
		resolveHeaders(data.getHeaders(), request);
		return request.async().method(data.getMethod(), Entity.entity(data.getRequestBody(), data.getMediaType()),
				responseType);

	}

	public <T> CompletionStage<T> asyncRequestRX(JaxrsRequestData data, Class<T> responseType) {
		WebTarget target = getTarget(data.getUrl(), data.getQueryParams(), data.getPathParams());

		Builder request = target.request(data.getMediaType());
		resolveHeaders(data.getHeaders(), request);
		return request.rx().method(data.getMethod(), Entity.entity(data.getRequestBody(), data.getMediaType()),
				responseType);

	}
	private WebTarget getTarget(String url, Map<String, Object> queryParams, Map<String, Object> pathParams) {

		WebTarget target = clientSource.getClient().target(url);
		if (queryParams != null)
			for (String key : queryParams.keySet())
				target = target.queryParam(key, queryParams.get(key));
		if (pathParams != null)
			target = target.resolveTemplates(pathParams);
		return target;
	}

	private void resolveHeaders(Map<String, Object> headers, Builder request) {
		if (headers != null)
			headers.forEach((k, v) -> request.header(k, v));
	}
}
