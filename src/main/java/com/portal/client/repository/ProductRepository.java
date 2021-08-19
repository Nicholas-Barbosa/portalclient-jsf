package com.portal.client.repository;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.portal.client.dto.ProductPage;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.dto.ProductTechDetailJson;
import com.portal.client.vo.Product;

public interface ProductRepository {

	ProductPageDTO find(int page, int pageSize);

	Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description);

	Optional<Product> findByCode(String code, String customerCode, String store);

	Future<ProductPage> findByCodeAsync(String code, String customerCode, String store) throws ExecutionException;

	Future<ProductPage> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws ExecutionException;

	ProductTechDetailJson findTechDetails(String commercialCode);
}
