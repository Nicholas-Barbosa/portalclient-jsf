package com.portal.pojo;

import java.time.LocalDateTime;

public class ProcessingExceptionMeta {

	private String entity;

	private int code;

	private LocalDateTime threwAt;

	public ProcessingExceptionMeta(String entity, int code) {
		super();
		this.entity = entity;
		this.code = code;
		this.threwAt = LocalDateTime.now();
	}

	public String getEntity() {
		return entity;
	}

	public int getCode() {
		return code;
	}

	public LocalDateTime getThrewAt() {
		return threwAt;
	}

}
