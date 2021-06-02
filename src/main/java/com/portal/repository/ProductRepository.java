package com.portal.repository;

import java.util.Optional;
import java.util.concurrent.Future;

import javax.ws.rs.ProcessingException;

import com.portal.dto.NoPageProductResponseDTO;
import com.portal.dto.ProductPageDTO;

public interface ProductRepository {

	NoPageProductResponseDTO getByCode(String code) throws ProcessingException;

	Future<NoPageProductResponseDTO> getByCodeAsync(String code);

	ProductPageDTO getAllByPage(int page, int pageSize) throws ProcessingException;

	Optional<ProductPageDTO> getByDescription(int page, int pageSize, String description) throws ProcessingException;
}
