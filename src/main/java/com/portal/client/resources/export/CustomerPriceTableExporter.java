package com.portal.client.resources.export;

import com.portal.client.vo.Customer;

public interface CustomerPriceTableExporter {

	byte[]toExcel(Customer customer);
}
