package com.portal.java.resources.export;

import com.portal.java.dto.Order;

public interface OrderExport {

	byte[] export(Order order, ExportType type);

}
