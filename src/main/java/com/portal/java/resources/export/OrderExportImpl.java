package com.portal.java.resources.export;

import javax.inject.Inject;

import com.portal.java.resources.export.report.jasper.OrderJasperReport;
import com.portal.java.resources.export.report.jasper.OrderReport;

public class OrderExportImpl implements OrderExport {

	@Inject
	private OrderReport budgetReport;

	@Override
	public byte[] export(ExportEntity export, ExportType type) {
		switch (type) {
		case PDF:
			return budgetReport.export((OrderJasperReport) export, type);
		case EXCEL:
			return budgetReport.export((OrderJasperReport) export, type);
		case EXCEL_CALC_CONFERENCE:
			break;
		}
		throw new IllegalArgumentException(type + " not supported");
	}

}
