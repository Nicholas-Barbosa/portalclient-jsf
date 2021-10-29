package com.portal.client.repository;

import java.util.List;
import java.util.Optional;

import com.portal.client.dto.ProductPriceListWrapper.ProductPriceList;

public interface ProductPriceListRepository {

	Optional<List<ProductPriceList>> find(String customerCode, String customerStore);
}
