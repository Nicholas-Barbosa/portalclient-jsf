package com.portal.client.export;

import com.portal.client.dto.BudgetRequest;

public interface OrderExport {

	byte[] export(BudgetRequest order, OrderExportType type);

}
