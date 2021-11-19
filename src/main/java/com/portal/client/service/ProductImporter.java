package com.portal.client.service;

import java.util.List;

import com.portal.client.dto.BatchProductSearchDataWrapper.BatchProductData;
import com.portal.client.dto.ProductXlsxFileReadLayout;
import com.portal.client.dto.ProductXlsxFileReadProjection;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;

public interface ProductImporter {

	List<ProductXlsxFileReadProjection> read(ProductXlsxFileReadLayout fileLayout);

	BatchProductData performBatchSearch(List<ProductXlsxFileReadProjection> items, String customerCode, String customerStore)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
