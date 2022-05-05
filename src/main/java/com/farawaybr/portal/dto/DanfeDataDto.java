package com.farawaybr.portal.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class DanfeDataDto {

	private String streamBase64;

	@JsonbCreator
	public DanfeDataDto(@JsonbProperty("danfe") String streamBase64) {
		super();
		this.streamBase64 = streamBase64;
	}

	public String getStreamBase64() {
		return streamBase64;
	}
}
