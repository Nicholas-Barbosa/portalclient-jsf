package com.portal.client.service;

import java.util.List;

import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.dto.ProductFileReadLayout;
import com.portal.client.dto.ProductImporterExtractedData;

public abstract class ProductImporter {

	public BatchProductSearchDataWrapper run(ProductFileReadLayout layout) {
		return this.parseData(this.extractData(layout), layout.getCustomerCode(), layout.getCustomerStore());

	}

	abstract List<ProductImporterExtractedData> extractData(ProductFileReadLayout layout);

	abstract BatchProductSearchDataWrapper parseData(List<ProductImporterExtractedData> datas, String customerCode,
			String customerStore);
}
