package com.portal.client.export;

import com.portal.client.dto.BudgetToSave;

public interface OrderExport {

	byte[] export(BudgetToSave order, OrderExportType type);

}
