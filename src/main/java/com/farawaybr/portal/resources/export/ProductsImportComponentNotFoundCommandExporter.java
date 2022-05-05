package com.farawaybr.portal.resources.export;

import com.farawaybr.portal.vo.WrapperProduct404Error.Product404Error;

public interface ProductsImportComponentNotFoundCommandExporter {

	byte[] execute(Product404Error[] products);
}
