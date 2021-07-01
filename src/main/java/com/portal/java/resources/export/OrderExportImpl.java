package com.portal.java.resources.export;

import java.io.Serializable;

import javax.inject.Inject;

import com.portal.java.dto.Order;
import com.portal.java.resources.export.excel.OrderExcelCalculusConference;
import com.portal.java.resources.export.report.jasper.OrderJasperReport;
import com.portal.java.resources.export.report.jasper.OrderReport;

public class OrderExportImpl implements OrderExport,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357786599377852612L;

	@Inject
	private OrderReport orderReport;

	@Inject
	private OrderExcelCalculusConference orderExcelCalculusConference;

	@Override
	public byte[] export(Order order, ExportType type) {
		switch (type) {
		case PDF:
			return orderReport.export(new OrderJasperReport(order), type);
		case EXCEL:
			return orderReport.export(new OrderJasperReport(order), type);
		case EXCEL_CALC_CONFERENCE:
			return orderExcelCalculusConference.createWorkbook(order);
		}
		throw new IllegalArgumentException(type + " not supported");
	}

}
