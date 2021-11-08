package com.portal.client.service.export.jasper;

import com.portal.client.dto.BudgetJasperForm;

public interface BudgetReport {

	byte[] process(BudgetJasperForm form, JasperReportType type);

}
