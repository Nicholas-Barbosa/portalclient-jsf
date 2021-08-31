package com.portal.client.dto;

import com.portal.client.export.OrderExportType;

public class OrderExporterForm {

	private String fileName;
	private OrderExportType type;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public OrderExportType getType() {
		return type;
	}

	public void setType(OrderExportType type) {
		this.type = type;
	}

	public void checkFileExtension() {
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
