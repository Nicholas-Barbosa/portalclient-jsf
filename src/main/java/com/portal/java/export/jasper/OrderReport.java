package com.portal.java.export.jasper;

import com.portal.java.export.OrderExportType;

public interface OrderReport {

	byte[] export(OrderJasper budget, OrderExportType type);

}
