package com.portal.java.resources.export.report.jasper;

import com.portal.java.resources.export.ExportType;

public interface OrderReport {

	byte[] export(OrderJasperReport budget, ExportType type);

}
