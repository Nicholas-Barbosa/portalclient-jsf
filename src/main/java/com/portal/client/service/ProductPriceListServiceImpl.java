package com.portal.client.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.ProductPriceListWrapper.ProductPriceList;
import com.portal.client.repository.ProductPriceListRepository;

@ApplicationScoped
public class ProductPriceListServiceImpl implements ProductPriceListService {

	@Inject
	private ProductPriceListRepository repository;

	@Override
	public Optional<List<ProductPriceList>> find(String customerCode, String customerStore) {
		// TODO Auto-generated method stub
		return repository.find(customerCode, customerStore);
	}

}
