package com.portal.java.http;

public enum ContentType {

	PDF("application/pdf"), EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	private final String type;

	private ContentType(String t) {
		this.type = t;
	}

	public String getType() {
		return type;
	}
}
