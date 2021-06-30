package com.portal.java.resources.export;

public enum ExportType {

	PDF("application/pdf"), EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	EXCEL_CALC_CONFERENCE("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	private final String type;

	private ExportType(String t) {
		this.type = t;
	}

	public String getType() {
		return type;
	}
}
