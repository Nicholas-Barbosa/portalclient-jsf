package com.portal.jasper.service;

import com.portal.dto.BudgetJasperReportDTO;

public interface BudgetReport {

	byte[] export(BudgetJasperReportDTO budget, String type);

	byte[] toPdf(BudgetJasperReportDTO budget);

	byte[] toExcel(BudgetJasperReportDTO budget);
}
