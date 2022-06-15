package com.farawaybr.portal.service;

import java.util.List;

import com.farawaybr.portal.dto.ExtractedDataPhase;
import com.farawaybr.portal.microsoft.excel.RowObject;

public interface ObserverProductImporter {

	void onExtractedData(ExtractedDataPhase jessionId);

	void onMismatchTypeCells(List<RowObject>rows);

	void onMismatchProductsMultiple();
	
	

}
