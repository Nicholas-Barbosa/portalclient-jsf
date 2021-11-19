package com.portal.client.service.export.jasper;

import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.service.export.jasper.service.JasperReportType;

public interface BudgetReport {

	byte[] process(BudgetJasperForm form, JasperReportType type);

}
