package com.portal.repository;

import java.util.Optional;
import java.util.concurrent.Future;

import com.portal.dto.NoPageProductResponseDTO;
import com.portal.dto.ProductPageDTO;

public interface ProductRepository {

	NoPageProductResponseDTO getByCode(String code);

	Future<NoPageProductResponseDTO> getByCodeAsync(String code);

	ProductPageDTO getAllByPage(int page, int pageSize);

	Optional<ProductPageDTO> getByDescription(int page, int pageSize, String description);
}
