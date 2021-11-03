package com.portal.client.service;

import java.util.List;
import java.util.Optional;

import com.portal.client.dto.ProductPriceTabletWrapper.ProductPriceTable;

public interface ProductPriceTableService {

	Optional<List<ProductPriceTable>> find(String customerCode, String customerStore);
}
