package com.portal.java.export;

import com.portal.java.dto.Order;

public interface OrderExport {

	byte[] export(Order order, OrderExportType type);

}
