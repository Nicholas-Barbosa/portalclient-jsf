package com.portal.client.resources.export.jasper;

import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.resources.export.jasper.service.JasperReportType;

public interface BudgetReport {

	byte[] generate(BudgetJasperForm form, JasperReportType type);

}
