package com.portal.client.resources.export;

import com.portal.client.vo.WrapperProduct404Error.Product404Error;

public interface ProductsImportComponentNotFoundCommandExporter {

	byte[] execute(Product404Error[] products);
}
