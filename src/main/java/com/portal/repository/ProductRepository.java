package com.portal.repository;

import java.util.Optional;

import com.portal.pojo.ProductPage;
import com.portal.pojo.Product;

public interface ProductRepository {

	Optional<Product> getByCode(String code);
	
	ProductPage getAllByPage(int page,int pageSize);
	
	ProductPage getByDescription(int page,int pageSize,String description);
}
