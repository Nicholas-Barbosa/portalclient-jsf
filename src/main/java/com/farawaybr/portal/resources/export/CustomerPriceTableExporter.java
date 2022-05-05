package com.farawaybr.portal.resources.export;

import com.farawaybr.portal.vo.Customer;

public interface CustomerPriceTableExporter {

	byte[]toExcel(Customer customer);
}
