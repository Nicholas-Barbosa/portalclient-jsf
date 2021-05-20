package com.portal.jasper.service;

import com.portal.dto.BudgetJasperReportDTO;
import com.portal.http.ContentType;

public interface BudgetReport {

	byte[] export(BudgetJasperReportDTO budget, ContentType type);

	byte[] toPdf(BudgetJasperReportDTO budget);

	byte[] toExcel(BudgetJasperReportDTO budget);
}
