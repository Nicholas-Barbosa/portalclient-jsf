package com.portal.service;

import java.util.Optional;

import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;

public interface ProductService {

	ProductPageDTO findByDescription(String descriptio, int page, int pageSize);

	Optional<ProductDTO> findByCode(String code);
}
