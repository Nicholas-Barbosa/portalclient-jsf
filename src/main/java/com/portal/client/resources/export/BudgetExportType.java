package com.portal.client.resources.export;

public enum BudgetExportType {

	PDF("application/pdf"), EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	EXCEL_CALC_CONFERENCE("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	private final String type;

	private BudgetExportType(String t) {
		this.type = t;
	}

	public String getType() {
		return type;
	}
}
