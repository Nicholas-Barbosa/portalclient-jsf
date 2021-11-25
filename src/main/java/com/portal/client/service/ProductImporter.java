package com.portal.client.service;

import java.util.List;

import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.dto.ProductFileReadLayout;
import com.portal.client.dto.ProductImporterExtractedData;

public interface ProductImporter {

	List<ProductImporterExtractedData> extractData(ProductFileReadLayout layout);

	BatchProductSearchDataWrapper parseData(List<ProductImporterExtractedData> datas, String customerCode,
			String customerStore);
}
