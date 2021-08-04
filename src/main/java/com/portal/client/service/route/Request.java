package com.portal.client.service.route;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

public class Request {

	private LocalDateTime createdAt;
	private HttpServletRequest request;

	public Request(LocalDateTime createdAt, HttpServletRequest request) {
		super();
		this.createdAt = createdAt;
		this.request = request;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public static class RequestB {
		private HttpServletRequest request;

		public static RequestB getBuilder() {
			return new RequestB();
		}

		public RequestB withServlet(HttpServletRequest request) {
			this.request = request;
			return this;
		}

		public Request build() {
			return new Request(LocalDateTime.now(), request);
		}
	}
}
