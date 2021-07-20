package com.portal.client.export.jasper;

import com.portal.client.export.OrderExportType;

public interface OrderReport {

	byte[] export(OrderJasper budget, OrderExportType type);

}
