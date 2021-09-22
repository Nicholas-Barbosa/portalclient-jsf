package com.portal.client.service.crud;

import java.util.Optional;

import com.portal.client.dto.CustomerOnOrder.CustomerType;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.service.ServiceSerializable;
import com.portal.client.vo.Product;

public interface ProductService extends ServiceSerializable {

	Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize);

	Optional<Product> findByCode(String code, String customerCode, String customerStore, String state,
			String sellerType, CustomerType customerType);

	void loadImage(Product product);

	void loadTechDetails(Product product);
}
