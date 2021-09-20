package com.portal.client.jaxrs.client;

import java.util.Map;

public class WebTargetData {

	private String url, token, prefixToken;
	private Map<String, Object> queryParams, pathParams;

	public WebTargetData(String uri, String token, String prefixToken, Map<String, Object> queryParams,
			Map<String, Object> pathParams) {
		super();
		this.url = uri;
		this.token = token;
		this.prefixToken = prefixToken;
		this.queryParams = queryParams;
		this.pathParams = pathParams;
	}

	public String getUrl() {
		return url;
	}

	public String getToken() {
		return token;
	}

	public String getPrefixToken() {
		return prefixToken;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public Map<String, Object> getPathParams() {
		return pathParams;
	}

}
