package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.client.rest.auth.AuthenticatedRestClient;
import com.portal.client.vo.FinancialBondsPage;

public class FinancialBondsRepositoryImpl implements FinancialBondsRepository {

	@Inject
	@OAuth2RestAuth
	private AuthenticatedRestClient restClient;

	@Override
	public FinancialBondsPage find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");
		return restClient.get("ORCAMENTO_API", "titles", FinancialBondsPage.class, queryParams, null,
				MediaType.APPLICATION_JSON);
	}

	@Override
	public FinancialBondsPage findByCustomerCodeStore(int page, int pageSize, String code, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");
		return restClient.get("ORCAMENTO_API", "titles/{code}/loja/{store}", FinancialBondsPage.class, queryParams,
				Map.of("code", code, "store", store), MediaType.APPLICATION_JSON);
	}

}
