package com.portal.client.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.BudgetSavedResponse;
import com.portal.client.dto.BudgetToSaveJsonSerializable;
import com.portal.client.dto.BudgetToUpdateDTO;
import com.portal.client.security.api.helper.APIHelper;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Page;

@ApplicationScoped
public class BudgetRepositoryImpl extends OptionalEmptyRepository implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final TokenedRestClient restClient;
	private APIHelper protheusApiHelper;

	public BudgetRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public BudgetRepositoryImpl(TokenedRestClient restClient,  APIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	public Page<Budget> findAll(int page, int pageSize) {
		StringBuilder endpointURL = new StringBuilder(protheusApiHelper.getBaseUrl());
		endpointURL.append("/budgets");
		return restClient.get(endpointURL.toString(), protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(),
				BudgetPage.class, Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC"), null,
				MediaType.APPLICATION_JSON);
	}

	@Override
	public void save(Budget budget) {
		BudgetSavedResponse response = restClient.post(protheusApiHelper.buildEndpoint("budgets"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), BudgetSavedResponse.class, null, null,
				BudgetToSaveJsonSerializable.of(budget), MediaType.APPLICATION_JSON);
		budget.setCode(response.getCode());
	}

	@Override
	public Optional<BudgetFullProjection> findByCode(String code) {
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("budgets/{code}"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), BudgetFullProjection.class, null,
				Map.of("code", code), MediaType.APPLICATION_JSON));

	}

	
	

	@Override
	public void update(Budget budget) {
		BudgetToUpdateDTO toUpdate = new BudgetToUpdateDTO(budget);
		restClient.put(protheusApiHelper.buildEndpoint("budgets/{id}"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), String.class, null, Map.of("id", budget.getCode()), toUpdate,
				"application/json");
	}
}
