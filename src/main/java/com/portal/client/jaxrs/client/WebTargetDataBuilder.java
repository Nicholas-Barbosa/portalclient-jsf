package com.portal.client.jaxrs.client;

import java.util.Map;

public class WebTargetDataBuilder {

	private String uri, token, prefixToken;
	private Map<String, Object> queryParams, pathParams;

	public static WebTargetDataBuilder getInstance() {
		return new WebTargetDataBuilder();
	}

	public WebTargetDataBuilder withUri(String uri) {
		this.uri = uri;
		return this;
	}

	public WebTargetDataBuilder withToken(String token) {
		this.token = token;
		return this;
	}

	public WebTargetDataBuilder withPrefixToken(String pr) {
		this.prefixToken = pr;
		return this;
	}

	public WebTargetDataBuilder withQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
		return this;
	}

	public WebTargetDataBuilder withPathParams(Map<String, Object> pathParams) {
		this.pathParams = pathParams;
		return this;
	}

	public WebTargetData build() {
		return new WebTargetData(uri, token, prefixToken, queryParams, pathParams);

	}
}
