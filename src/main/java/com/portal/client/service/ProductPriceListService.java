package com.portal.client.service;

import java.util.List;
import java.util.Optional;

import com.portal.client.dto.ProductPriceListWrapper.ProductPriceList;

public interface ProductPriceListService {

	Optional<List<ProductPriceList>> find(String customerCode, String customerStore);
}
