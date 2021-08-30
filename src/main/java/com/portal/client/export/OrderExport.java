package com.portal.client.export;

import com.portal.client.vo.Budget;

public interface OrderExport {

	byte[] export(Budget order, OrderExportType type);

}
