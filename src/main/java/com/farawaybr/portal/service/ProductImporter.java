package com.farawaybr.portal.service;

import java.util.List;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.ProductFileReadLayout;
import com.farawaybr.portal.dto.ProductImportXlsxCustomizedRow;
import com.farawaybr.portal.dto.ProductImportXlsxLayout;
import com.farawaybr.portal.dto.ProductImporterExtractedData;
import com.farawaybr.portal.microsoft.excel.RowObject;

public interface ProductImporter {

	List<ProductImporterExtractedData> extractData(ProductFileReadLayout layout);

	List<RowObject> extractData(byte[]xlsxstreams);

	List<ProductImportXlsxCustomizedRow> customizeExtractedData(ProductImportXlsxLayout layout);
	
	
	BatchProductSearchDataWrapper parseData(List<ProductImporterExtractedData> datas, String customerCode,
			String customerStore);
}
