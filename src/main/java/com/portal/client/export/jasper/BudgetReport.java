package com.portal.client.export.jasper;

import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.export.OrderExportType;

public interface BudgetReport {

	byte[] export(BudgetJasperForm form, OrderExportType type);

}
