package com.farawaybr.portal.service;

import java.util.List;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.ProductFileReadLayout;
import com.farawaybr.portal.dto.ProductImporterExtractedData;

public interface ProductImporter {

	List<ProductImporterExtractedData> extractData(ProductFileReadLayout layout);

	BatchProductSearchDataWrapper parseData(List<ProductImporterExtractedData> datas, String customerCode,
			String customerStore);
}
