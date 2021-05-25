package com.portal.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.portal.dto.NoPageProductResponseDTO;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;
import com.portal.repository.ProductRepository;

@Stateless
public class ProductServiceImpl implements ProductService {

	@Inject
	private ProductRepository productRepository;

	@Override
	public ProductPageDTO findByDescription(String descriptio, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ProductDTO> findByCode(String code) {
		NoPageProductResponseDTO noPage = productRepository.getByCode(code);
		return noPage == null ? Optional.empty() : Optional.of(noPage.getProducts().get(0));
	}

}
