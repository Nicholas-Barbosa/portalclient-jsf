package com.portal.client.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.cdi.aop.OptionalEmptyRepository;
import com.portal.client.dto.FinancialBondsPage;
import com.portal.client.security.api.helper.APIHelper;

public class BillsToReceiveRepositoryImpl extends OptionalEmptyRepository implements BillsToReceiveRepository {

	private TokenedRestClient restClient;
	private APIHelper protheusApiHelper;
	
	@Inject
	public BillsToReceiveRepositoryImpl(TokenedRestClient restClient, APIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	public Optional<FinancialBondsPage> find(int page, int pageSize) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("titles"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), FinancialBondsPage.class, queryParams, null, MediaType.APPLICATION_JSON));

	}

	@Override
	public Optional<FinancialBondsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");

		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("titles/{code}/loja/{store}"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), FinancialBondsPage.class, queryParams,
				Map.of("code", code, "store", store), MediaType.APPLICATION_JSON));

	}

}
