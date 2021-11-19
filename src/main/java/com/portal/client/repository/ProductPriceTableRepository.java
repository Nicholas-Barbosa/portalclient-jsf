package com.portal.client.repository;

import java.util.List;
import java.util.Optional;

import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTable;

public interface ProductPriceTableRepository {

	Optional<List<ProductPriceTable>> find(String customerCode, String customerStore);
}
