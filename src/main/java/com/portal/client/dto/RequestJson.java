package com.portal.client.dto;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.portal.client.service.route.Request;

public class RequestJson implements Comparable<RequestJson> {

	private Request request;

	public RequestJson(Request request) {
		this.request = request;

	}

	@JsonbTransient
	public Request getRequest() {
		return request;
	}

	@JsonbProperty("requested_at")
	public LocalDateTime getRequestedAt() {
		return request.getCreatedAt();
	}

	@JsonbProperty("request_uri")
	public String getPath() {
		return request.getRequestUri();
	}

	@JsonbProperty("client_locale")
	public String getLocale() {
		return request.getLocale().toLanguageTag();
	}

	@JsonbProperty("client_ip")
	public String getIp() {
		return request.getClientIp();
	}

	@JsonbProperty
	public String getServerName() {
		return request.getServerName();
	}

	@Override
	public int compareTo(RequestJson o) {
		// TODO Auto-generated method stub
		return o.getRequestedAt().compareTo(getRequestedAt());
	}

}
