package com.portal.java.resources.export;

import javax.inject.Inject;

import com.portal.java.dto.Order;
import com.portal.java.resources.export.report.jasper.OrderJasperReport;
import com.portal.java.resources.export.report.jasper.OrderReport;

public class OrderExportImpl implements OrderExport {

	@Inject
	private OrderReport budgetReport;

	@Override
	public byte[] export(Order order, ExportType type) {
		switch (type) {
		case PDF:
			return budgetReport.export(new OrderJasperReport(order), type);
		case EXCEL:
			return budgetReport.export(new OrderJasperReport(order), type);
		case EXCEL_CALC_CONFERENCE:

			break;
		}
		throw new IllegalArgumentException(type + " not supported");
	}

}
