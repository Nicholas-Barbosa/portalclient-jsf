package com.portal.java.jasper.service;

import com.portal.java.dto.BudgetJasperReportDTO;
import com.portal.java.http.ContentType;

public interface BudgetReport {

	byte[] export(BudgetJasperReportDTO budget, ContentType type);

	byte[] toPdf(BudgetJasperReportDTO budget);

	byte[] toExcel(BudgetJasperReportDTO budget);
}
