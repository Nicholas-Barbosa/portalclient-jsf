package com.farawaybr.portal.resources.export;

import com.farawaybr.portal.vo.WrapperProductBatchSearchEndpointError.ProductBatchSearchEndpointError;

public interface ProductsImportComponentNotFoundCommandExporter {

	byte[] execute(ProductBatchSearchEndpointError[] products);
}
