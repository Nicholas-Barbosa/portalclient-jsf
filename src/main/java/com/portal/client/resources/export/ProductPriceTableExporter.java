package com.portal.client.resources.export;

import com.portal.client.dto.Customer;

public interface ProductPriceTableExporter {

	byte[]toExcel(Customer customer);
}
