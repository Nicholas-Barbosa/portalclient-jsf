package com.portal.client.export;

import com.portal.client.dto.BaseBudget;

public interface OrderExport {

	byte[] export(BaseBudget order, OrderExportType type);

}
