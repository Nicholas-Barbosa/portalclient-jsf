package com.portal.jasper.service;

import com.portal.dto.BudgetJasperReportDTO;

public interface BudgetReport {

	byte[] toPdf(BudgetJasperReportDTO budget);
}
