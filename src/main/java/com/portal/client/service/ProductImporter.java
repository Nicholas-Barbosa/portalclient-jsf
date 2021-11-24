package com.portal.client.service;

import java.util.List;

import com.portal.client.dto.ProductFileReadLayout;
import com.portal.client.dto.ProductImporterExtractedData;
import com.portal.client.vo.Product;

public abstract class ProductImporter {

	public  List<Product> run(ProductFileReadLayout layout) {
		return this.parseData(this.extractData(layout));

	}

	abstract List<ProductImporterExtractedData> extractData(ProductFileReadLayout layout);

	abstract List<Product> parseData(List<ProductImporterExtractedData> datas);
}
