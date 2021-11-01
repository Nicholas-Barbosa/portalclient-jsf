package com.portal.client.export;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.portal.client.dto.ProductPriceTabletWrapper.ProductPriceTable;

@ApplicationScoped
public class ProductPriceTableExporterImpl implements ProductPriceTableExporter{

	@Override
	public byte[] toExcel(String customerCode, List<ProductPriceTable> table) {
		// TODO Auto-generated method stub
		return null;
	}

}
