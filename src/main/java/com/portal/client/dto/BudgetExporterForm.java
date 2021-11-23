package com.portal.client.dto;

import com.portal.client.resources.export.BudgetExportType;

public class BudgetExporterForm {

	private String fileName;
	private BudgetExportType type;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BudgetExportType getType() {
		return type;
	}

	public void setType(BudgetExportType type) {
		this.type = type;
	}

	public void appendExtension() {
		fileName = fileName.strip();
		int index = this.fileName.lastIndexOf(".");
		if (index == -1) {
			switch (type) {
			case EXCEL:
				fileName += ".xlsx";
				break;
			case EXCEL_CALC_CONFERENCE:
				fileName += ".xlsx";
				break;
			case PDF:
				fileName += ".pdf";
				break;
			}

		}
	}
}
