package com.portal.client.export.jasper;

import com.portal.client.export.OrderExportType;

public interface BudgetReport {

	byte[] export(BudgetJasper budget, OrderExportType type);

}
