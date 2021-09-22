package com.portal.client.export;

import com.portal.client.vo.Budget;

public interface BudgetExporter {

	byte[] export(Budget budget, OrderExportType type);

}
