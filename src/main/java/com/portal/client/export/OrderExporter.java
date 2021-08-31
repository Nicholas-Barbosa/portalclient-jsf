package com.portal.client.export;

import com.portal.client.vo.Order;

public interface OrderExporter {

	byte[] export(Order order, OrderExportType type);

}
