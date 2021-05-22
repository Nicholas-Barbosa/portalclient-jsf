package com.portal.repository;

import java.util.Optional;

import javax.ws.rs.ProcessingException;

import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;

public interface ProductRepository {

	Optional<ProductDTO> getByCode(String code) throws ProcessingException;

	ProductPageDTO getAllByPage(int page, int pageSize) throws ProcessingException;

	Optional<ProductPageDTO> getByDescription(int page, int pageSize, String description) throws ProcessingException;
}
