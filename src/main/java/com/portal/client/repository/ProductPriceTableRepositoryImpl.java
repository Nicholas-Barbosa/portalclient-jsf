package com.portal.client.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.cdi.aop.OptionalEmptyRepository;
import com.portal.client.dto.ProductPriceTableWrapper;
import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTable;
import com.portal.client.security.api.helper.ProtheusAPIHelper;

@ApplicationScoped
public class ProductPriceTableRepositoryImpl extends OptionalEmptyRepository implements ProductPriceTableRepository {

	@Inject
	private TokenedRestClient restClient;

	@Inject
	private ProtheusAPIHelper protheusApi;

	@Override
	public Optional<List<ProductPriceTable>> find(String customerCode, String customerStore) {
		// TODO Auto-generated method stub
		return Optional
				.of(restClient
						.get(protheusApi.buildEndpoint("tables/{customer}/loja/{store}"), protheusApi.getToken(),
								protheusApi.getTokenPrefix(), ProductPriceTableWrapper.class, null,
								Map.of("customer", customerCode, "store", customerStore), "application/json")
						.getList());
	}

}
