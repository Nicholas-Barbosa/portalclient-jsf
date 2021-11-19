package com.portal.client.resources.export;

import java.util.List;

import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTable;

public interface ProductPriceTableExporter {

	byte[]toExcel(String customerCode,List<ProductPriceTable> table);
}
