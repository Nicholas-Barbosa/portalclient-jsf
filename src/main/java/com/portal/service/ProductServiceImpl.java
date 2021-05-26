package com.portal.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.dto.NoPageProductResponseDTO;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;
import com.portal.repository.ProductRepository;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7904939451132445103L;
	@Inject
	private ProductRepository productRepository;

	@Override
	public Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize) {
		// TODO Auto-generated method stub
		return productRepository.getByDescription(pageSize, pageSize, descriptio);
	}

	@Override
	public Optional<ProductDTO> findByCode(String code) {
		NoPageProductResponseDTO noPage = productRepository.getByCode(code);
		return noPage == null ? Optional.empty() : Optional.of(noPage.getProducts().get(0));
	}

}
