package com.farawaybr.portal.jaxrs.client;

import java.util.HashMap;
import java.util.Map;

public class JaxrsRequestData {

	private String url, method, mediaType;
	private Object requestBody;
	private Map<String, Object> queryParams, pathParams, headers;

	public JaxrsRequestData() {
		// TODO Auto-generated constructor stub
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public String getMediaType() {
		return mediaType;
	}

	public Object getRequestBody() {
		return requestBody;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public Map<String, Object> getPathParams() {
		return pathParams;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public static class JaxrsRequestDataBuilder extends JaxrsRequestData {

		public static JaxrsRequestDataBuilder getInstance() {
			return new JaxrsRequestDataBuilder();
		}

		public JaxrsRequestDataBuilder url(String url) {
			super.url = url;
			return this;
		}

		public JaxrsRequestDataBuilder method(String method) {
			super.method = method;
			return this;
		}

		public JaxrsRequestDataBuilder mediaType(String mediaType) {
			super.mediaType = mediaType;
			return this;
		}

		public JaxrsRequestDataBuilder requestBody(Object requestBody) {
			super.requestBody = requestBody;
			return this;
		}

		public JaxrsRequestDataBuilder queryParams(Map<String, Object> queryParams) {
			super.queryParams = queryParams;
			return this;
		}

		public JaxrsRequestDataBuilder pathParams(Map<String, Object> pathParams) {
			super.pathParams = pathParams;
			return this;
		}

		public JaxrsRequestDataBuilder headers(Map<String, Object> headers) {
			super.headers = headers;
			return this;
		}

		public JaxrsRequestDataBuilder header(String header, Object value) {
			if (super.headers == null)
				super.headers = new HashMap<>();
			super.headers.put(header, value);
			return this;
		}

		public JaxrsRequestData build() {
			return this;
		}
	}
}
