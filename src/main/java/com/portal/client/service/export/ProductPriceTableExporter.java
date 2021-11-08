package com.portal.client.service.export;

import java.util.List;

import com.portal.client.dto.ProductPriceTabletWrapper.ProductPriceTable;

public interface ProductPriceTableExporter {

	byte[]toExcel(String customerCode,List<ProductPriceTable> table);
}
