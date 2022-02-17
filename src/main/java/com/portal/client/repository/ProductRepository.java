package com.portal.client.repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.dto.ProductWrapper;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.dto.ProductTechDetailJson;
import com.portal.client.dto.ProductToFind;
import com.portal.client.vo.Product;

public interface ProductRepository {

	ProductPageDTO find(int page, int pageSize);

	Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description);

	Optional<Product> findByCode(String code, String customerCode, String store);

	Future<ProductWrapper> findByCodeAsync(String code, String customerCode, String store) throws ExecutionException;

	Future<ProductWrapper> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws ExecutionException;

	ProductTechDetailJson findTechDetails(String commercialCode);

	void findStock(Product... products);

	BatchProductSearchDataWrapper batchProductSearch(String customerCode, String customerStore,
			Set<ProductToFind> products);
}
