package com.portal.client.rest;

public class QueryParam {

	private final String name;
	private final Object value;
	
	
	public QueryParam(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}
	
	
}
