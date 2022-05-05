package com.farawaybr.portal.repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.ProductPageDTO;
import com.farawaybr.portal.dto.ProductTechDetailJson;
import com.farawaybr.portal.dto.ProductToFind;
import com.farawaybr.portal.dto.ProductWrapper;
import com.farawaybr.portal.vo.Product;

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
