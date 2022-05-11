package com.farawaybr.portal.service.crud;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.farawaybr.portal.dto.ProductPageDTO;
import com.farawaybr.portal.service.ServiceSerializable;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.CustomerOnOrder.CustomerType;

public interface ProductService extends ServiceSerializable {

	Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize);

	Optional<Product> findByCode(String code, String customerCode, String customerStore, String state,
			String sellerType, CustomerType customerType) throws ExecutionException, InterruptedException;

	void loadImage(Product product);

	void loadTechDetails(Product product);

	void findStock(Product... products);

}
