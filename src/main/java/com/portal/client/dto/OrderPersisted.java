package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class OrderPersisted {

	private String code;

	@JsonbCreator
	public OrderPersisted(@JsonbProperty("order") String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
