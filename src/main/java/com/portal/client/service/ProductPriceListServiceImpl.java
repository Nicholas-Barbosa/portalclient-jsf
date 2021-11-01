package com.portal.client.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.ProductPriceTabletWrapper.ProductPriceTable;
import com.portal.client.repository.ProductPriceTableRepository;

@ApplicationScoped
public class ProductPriceListServiceImpl implements ProductPriceListService {

	@Inject
	private ProductPriceTableRepository repository;

	@Override
	public Optional<List<ProductPriceTable>> find(String customerCode, String customerStore) {
		// TODO Auto-generated method stub
		return repository.find(customerCode, customerStore);
	}

}
