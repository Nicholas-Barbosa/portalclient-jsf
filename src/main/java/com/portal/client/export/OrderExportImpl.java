package com.portal.client.export;

import java.io.Serializable;

import javax.inject.Inject;

import com.portal.client.export.jasper.BudgetJasper;
import com.portal.client.export.jasper.BudgetReport;
import com.portal.client.vo.Order;

public class OrderExportImpl implements OrderExporter,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357786599377852612L;

	@Inject
	private BudgetReport orderReport;

	@Inject
	private OrderExcelCalculusConference orderExcelCalculusConference;

	private OrderJsonHandler jsonHandler;
	@Override
	public byte[] export(Order order, OrderExportType type) {
		switch (type) {
		case PDF:
			return orderReport.export(new BudgetJasper(order), type);
		case EXCEL:
			return orderReport.export(new BudgetJasper(order), type);
		case EXCEL_CALC_CONFERENCE:
			return orderExcelCalculusConference.createWorkbook(order);
		case JSON:
			return jsonHandler.toJson(order);
		}
		throw new IllegalArgumentException(type + " not supported");
	}

}
