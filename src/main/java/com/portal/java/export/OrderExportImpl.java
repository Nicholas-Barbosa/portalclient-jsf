package com.portal.java.export;

import java.io.Serializable;

import javax.inject.Inject;

import com.portal.java.dto.Order;
import com.portal.java.export.jasper.OrderJasper;
import com.portal.java.export.jasper.OrderReport;

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
	public byte[] export(Order order, OrderExportType type) {
		switch (type) {
		case PDF:
			return orderReport.export(new OrderJasper(order), type);
		case EXCEL:
			return orderReport.export(new OrderJasper(order), type);
		case EXCEL_CALC_CONFERENCE:
			return orderExcelCalculusConference.createWorkbook(order);
		}
		throw new IllegalArgumentException(type + " not supported");
	}

}
