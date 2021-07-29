package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.BudgetSavedResponse;
import com.portal.client.dto.BudgetToSaveJsonSerializable;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;

@ApplicationScoped
public class BudgetRepositoryImpl implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final RestClient restClient;
	private final UserSessionAPIManager apiManager;

	private final String orcamentoKey = "ORCAMENTO_API";

	public BudgetRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public BudgetRepositoryImpl(RestClient restClient, UserSessionAPIManager apiManager) {
		super();
		this.restClient = restClient;
		this.apiManager = apiManager;
	}

	@Override
	public BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		ServerAPI api = apiManager.getAPI(orcamentoKey);
		StringBuilder endpointURL = new StringBuilder(api.getBasePath());
		endpointURL.append("/budgets");
		return restClient.get(endpointURL.toString(), api.getToken(), api.getTokenPrefix(), BudgetPage.class,
				Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC"), null, MediaType.APPLICATION_JSON);
	}

	@Override
	public BaseBudget save(BudgetToSaveJsonSerializable budget)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		ServerAPI api = apiManager.getAPI(orcamentoKey);

		BudgetSavedResponse response = restClient.post(apiManager.buildEndpoint(api, "budgets"), api.getToken(),
				api.getTokenPrefix(), BudgetSavedResponse.class, null, null, budget, MediaType.APPLICATION_JSON);
		budget.setIdCode(response.getCode());
		return budget;
	}

	@Override
	public Optional<BaseBudget> findByCode(String code)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		ServerAPI api = apiManager.getAPI(orcamentoKey);
		try {
			return Optional.of(restClient.get(apiManager.buildEndpoint(api, "budgets/{code}"), api.getToken(),
					api.getTokenPrefix(), BudgetFullProjection.class, null, Map.of("code", code),
					MediaType.APPLICATION_JSON));
		} catch (javax.ws.rs.NotFoundException e) {
			return Optional.empty();
		}
	}

}
