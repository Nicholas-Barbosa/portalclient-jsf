package com.portal.client.export;

import com.portal.client.dto.Order;

public interface OrderExport {

	byte[] export(Order order, OrderExportType type);

}
