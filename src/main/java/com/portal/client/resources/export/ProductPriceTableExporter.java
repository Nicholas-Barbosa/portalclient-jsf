package com.portal.client.resources.export;

import com.portal.client.dto.CustomerJson;

public interface ProductPriceTableExporter {

	byte[]toExcel(CustomerJson customer);
}
