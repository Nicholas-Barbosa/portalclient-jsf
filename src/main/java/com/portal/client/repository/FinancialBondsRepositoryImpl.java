package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.client.rest.RestClient;
import com.portal.client.dto.FinancialBondsPage;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;

public class FinancialBondsRepositoryImpl implements FinancialBondsRepository {

	private RestClient restClient;
	private UserSessionAPIManager apiManager;

	@Inject
	public FinancialBondsRepositoryImpl(RestClient restClient, UserSessionAPIManager endpointBuilder) {
		super();
		this.restClient = restClient;
		this.apiManager = endpointBuilder;
	}

	@Override
	public FinancialBondsPage find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");

		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		return restClient.get(apiManager.buildEndpoint(server, "titles"), server.getToken(), server.getTokenPrefix(),
				FinancialBondsPage.class, queryParams, null, MediaType.APPLICATION_JSON);
	}

	@Override
	public FinancialBondsPage findByCustomerCodeStore(int page, int pageSize, String code, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");

		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		return restClient.get(apiManager.buildEndpoint(server, "titles/{code}/loja/{store}"), server.getToken(),
				server.getTokenPrefix(), FinancialBondsPage.class, queryParams, Map.of("code", code, "store", store),
				MediaType.APPLICATION_JSON);
	}

}
