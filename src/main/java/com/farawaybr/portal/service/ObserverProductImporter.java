package com.farawaybr.portal.service;

import java.util.List;
import java.util.Set;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.BatchProductSearchDataWrapper.BatchProductSearchData;
import com.farawaybr.portal.dto.MultipleProductRowExcelData;
import com.farawaybr.portal.dto.ProductRowExcelData;

public interface ObserverProductImporter {

	void onComplete(BatchProductSearchDataWrapper products);

	void onMismatchTypeCells(List<ProductRowExcelData> rows);

	void onMismatchProductsMultiple(List<MultipleProductRowExcelData> products);

	void onProductsNotFound(List<ProductRowExcelData> products);

}
