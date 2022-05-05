package com.farawaybr.portal.resources.export.jasper;

import com.farawaybr.portal.dto.BudgetJasperForm;
import com.farawaybr.portal.resources.export.jasper.service.JasperReportType;

public interface BudgetReport {

	byte[] generate(BudgetJasperForm form, JasperReportType type);

}
