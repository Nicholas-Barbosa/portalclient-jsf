package com.portal.java.jasper.service;

import com.portal.java.dto.BudgetJasperReportDTO;
import com.portal.java.http.ReportType;

public interface BudgetReport {

	byte[] export(BudgetJasperReportDTO budget, ReportType type);

}
