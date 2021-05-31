package com.portal.service;

import java.util.Collection;
import java.util.Optional;

import com.portal.dto.BaseProductDTO;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;

public interface ProductService extends ServiceSerializable {

	Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize);

	Optional<ProductDTO> findByCode(String code);

	void loadImage(Collection<? extends BaseProductDTO> products);

	void loadImage(ProductDTO productDTO);
}
