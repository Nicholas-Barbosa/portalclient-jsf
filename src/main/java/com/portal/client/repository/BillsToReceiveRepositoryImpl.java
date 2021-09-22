package com.portal.client.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.FinancialBondsPage;
import com.portal.client.jaxrs.client.TokenedRestClient;
import com.portal.client.repository.aop.OptionalEmptyRepository;
import com.portal.client.security.APIManager;
import com.portal.client.security.api.ServerAPI;

public class BillsToReceiveRepositoryImpl extends OptionalEmptyRepository implements BillsToReceiveRepository {

	private TokenedRestClient restClient;
	private APIManager apiManager;

	@Inject
	public BillsToReceiveRepositoryImpl(TokenedRestClient restClient, APIManager endpointBuilder) {
		super();
		this.restClient = restClient;
		this.apiManager = endpointBuilder;
	}

	@Override
	public Optional<FinancialBondsPage> find(int page, int pageSize) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");
		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		return Optional.of(restClient.get(apiManager.buildEndpoint(server, "titles"), server.getToken(),
				server.getTokenPrefix(), FinancialBondsPage.class, queryParams, null, MediaType.APPLICATION_JSON));

	}

	@Override
	public Optional<FinancialBondsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");

		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		return Optional.of(restClient.get(apiManager.buildEndpoint(server, "titles/{code}/loja/{store}"),
				server.getToken(), server.getTokenPrefix(), FinancialBondsPage.class, queryParams,
				Map.of("code", code, "store", store), MediaType.APPLICATION_JSON));

	}

}
