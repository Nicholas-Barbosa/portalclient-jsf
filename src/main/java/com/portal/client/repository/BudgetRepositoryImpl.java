package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetEstimatedResultBuilder;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.BudgetSavedResponse;
import com.portal.client.dto.BudgetToSaveJsonSerializable;
import com.portal.client.dto.FormToEstimateBudget;
import com.portal.client.dto.ItemBudgetToEstimate;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.service.jsonb.JsonbService;
import com.portal.client.vo.BudgetEstimatedResultError;
import com.portal.client.vo.BudgetEstimatedResultError.ItemError;
import com.portal.client.vo.BudgetEstimatedResultSet;

@ApplicationScoped
public class BudgetRepositoryImpl implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final RestClient restClient;
	private final UserSessionAPIManager apiManager;
	private final JsonbService jsonbService;
	private final String orcamentoKey = "ORCAMENTO_API";

	public BudgetRepositoryImpl() {
		this(null, null, null);
	}

	@Inject
	public BudgetRepositoryImpl(RestClient restClient, UserSessionAPIManager apiManager, JsonbService jsonbService) {
		super();
		this.restClient = restClient;
		this.apiManager = apiManager;
		this.jsonbService = jsonbService;
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

	@Override
	public BudgetEstimatedResultSet estimate(String customerCode, String customerStore, Set<ItemBudgetToEstimate> items)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		ServerAPI server = apiManager.getAPI(orcamentoKey);

		FormToEstimateBudget toEstimate = new FormToEstimateBudget(customerCode, customerStore, items);
		try {
			BudgetEstimatedResultBuilder budgetBuilder = restClient.post(apiManager.buildEndpoint(server, "estimate"),
					server.getToken(), server.getTokenPrefix(), BudgetEstimatedResultBuilder.class, null, null,
					toEstimate, "application/json");
			return new BudgetEstimatedResultSet(true, budgetBuilder.build(), null);
		} catch (NotFoundException e) {
			ItemError[] errors = jsonbService.fromJson(e.getResponse().getEntity().toString(), ItemError[].class);

			BudgetEstimatedResultError error = new BudgetEstimatedResultError(404, false, errors);
			BudgetEstimatedResultSet resultSet = new BudgetEstimatedResultSet(false, null, error);

			return resultSet;
		}

	}

}
