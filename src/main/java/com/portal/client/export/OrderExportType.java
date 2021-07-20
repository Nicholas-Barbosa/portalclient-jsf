package com.portal.client.export;

public enum OrderExportType {

	PDF("application/pdf"), EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	EXCEL_CALC_CONFERENCE("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	private final String type;

	private OrderExportType(String t) {
		this.type = t;
	}

	public String getType() {
		return type;
	}
}
