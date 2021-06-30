package com.portal.java.http;

public enum ReportType {

	PDF("application/pdf"), EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	private final String type;

	private ReportType(String t) {
		this.type = t;
	}

	public String getType() {
		return type;
	}
}
